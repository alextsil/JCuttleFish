package obfuscations.layout;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.UnitSourceInitiator;
import pojo.UnitSource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public boolean obfuscate ( List<File> targetFiles )
    {
        for ( File file : targetFiles )
        {
            UnitSourceInitiator initiator = new UnitSourceInitiator();
            logger.debug( "path (String) extracted from File is : " + file.getPath() );
            UnitSource unitSource = initiator.fetchUnitSource( file.getPath() );
            Document document = new Document( unitSource.getSourceCode() );
            CompilationUnit cu = unitSource.getCompilationUnit();
            cu.recordModifications();

            //TODO: stream from file.
            Stream<String> variableNamesStream = Stream.of( "a", "b", "c", "d", "e", "f", "g", "h", "i" );
            Deque<String> obfuscatedVariableNames = variableNamesStream.distinct().collect( Collectors.toCollection( LinkedList::new ) );

            if ( !cu.types().isEmpty() )
            {
                //replace "if" with "typedecl.resolvebindings().isClass()";
                TypeDeclaration typeDecl = ( TypeDeclaration ) cu.types().get( 0 );
                if ( typeDecl.getFields().length != 0 )
                {
                    for ( FieldDeclaration fieldDeclaration : typeDecl.getFields() )
                    {
                        //get(0) translates to get variable declaration fragment.
                        VariableDeclarationFragment vdf = ( VariableDeclarationFragment ) fieldDeclaration.fragments().get( 0 );

                        SimpleName originalVarSimpleName = vdf.getName();

                        String obfuscatedVarName = obfuscatedVariableNames.pollFirst();

                        for ( MethodDeclaration methodDeclaration : typeDecl.getMethods() )
                        {
                            //debug var
                            Block methodBody = methodDeclaration.getBody();

                            List<SingleVariableDeclaration> methodParameters = methodDeclaration.parameters();
                            if ( !methodParameters.isEmpty() )
                            {
                                methodParameters.stream()
                                        .filter( svd -> svd.getName().getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                        .forEach( svd -> svd.getName().setIdentifier( obfuscatedVarName ) );
                            }

                            List<Statement> statements = methodDeclaration.getBody().statements();
                            for ( Statement statement : statements )
                            {
                                Expression expression = null;

                                if ( statement.getNodeType() == ASTNode.EXPRESSION_STATEMENT )
                                {
                                    expression = ( ( ExpressionStatement ) statement ).getExpression();
                                    if ( expression.getNodeType() == ASTNode.METHOD_INVOCATION )
                                    {
                                        MethodInvocation methodInvocation = ( MethodInvocation ) expression;
                                        List<SimpleName> arguments = methodInvocation.arguments();
                                        arguments.stream()
                                                .filter( argument -> argument.getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                                .forEach( argument -> argument.setIdentifier( obfuscatedVarName ) );
                                    }

                                    if ( expression.getNodeType() == ASTNode.ASSIGNMENT )
                                    {
                                        Assignment assignment = ( Assignment ) expression;
                                        if ( assignment.getLeftHandSide().getNodeType() == ASTNode.FIELD_ACCESS )
                                        {
                                            FieldAccess fieldAccess = ( FieldAccess ) assignment.getLeftHandSide();
                                            if ( fieldAccess.getName().getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                            {
                                                fieldAccess.getName().setIdentifier( obfuscatedVarName );
                                            }
                                        } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.SIMPLE_NAME )
                                        {
                                            SimpleName simpleName = ( SimpleName ) assignment.getLeftHandSide();
                                            if ( simpleName.getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                            {
                                                simpleName.setIdentifier( obfuscatedVarName );
                                            }
                                        }

                                        if ( assignment.getRightHandSide().getNodeType() == ASTNode.SIMPLE_NAME )
                                        {
                                            SimpleName simpleName = ( SimpleName ) assignment.getRightHandSide();
                                            if ( simpleName.getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                            {
                                                simpleName.setIdentifier( obfuscatedVarName );
                                            }
                                        }

                                    }
                                } else if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
                                {
                                    expression = ( ( ReturnStatement ) statement ).getExpression();

                                    if ( expression.getNodeType() == ASTNode.FIELD_ACCESS )
                                    {
                                        FieldAccess fieldAccess = ( FieldAccess ) expression;
                                        if ( fieldAccess.getName().getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                        {
                                            fieldAccess.getName().setIdentifier( obfuscatedVarName );
                                        }
                                    } else if ( expression.getNodeType() == ASTNode.SIMPLE_NAME )
                                    {
                                        SimpleName simpleName = (SimpleName ) expression;
                                        if ( simpleName.getIdentifier().toString().equals( originalVarSimpleName.getIdentifier() ) )
                                        {
                                            simpleName.setIdentifier( obfuscatedVarName );
                                        }
                                    }
                                } else if ( statement.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT )
                                {
                                    //Never userd. probably wrong casting.
                                    VariableDeclarationStatement vds = ( VariableDeclarationStatement ) statement;
                                    VariableDeclarationFragment localVdf = ( VariableDeclarationFragment ) vds.fragments().get( 0 );
                                    ClassInstanceCreation initializer = ( ClassInstanceCreation ) localVdf.getInitializer();
                                    for ( Object argumentObj : initializer.arguments() )
                                    {
                                        SimpleName simpleName = ( SimpleName ) argumentObj;
                                        if ( simpleName.getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                        {
                                            simpleName.setIdentifier( obfuscatedVarName );
                                        }
                                    }
                                } else
                                {
                                    logger.debug( "Not mapped yet" );
                                }
                            }
                        }
                        //Change declaration name after modifying all usages.
                        vdf.getName().setIdentifier( obfuscatedVarName );
                    }
                }
            }

            //Write changes to file. TODO: only write if there are changes.
            TextEdit edits = cu.rewrite( document, null );
            try
            {
                edits.apply( document );
                FileUtils.write(
                        //override for local testing
                        new File( "C:/test/JCuttleFish/src/main/java/pojo/UnitSource.java" ), document.get(), Charset.defaultCharset() );
            } catch ( BadLocationException e )
            {
                logger.error( "Bad location exception when applying edits. Message is : " + e.getMessage() );
            } catch ( IOException e2 )
            {
                logger.error( "IO exception. Message is : " + e2.getMessage() );
            }
        }

        return true;

    }
}
