/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foajena;

import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

/**
 *
 * @author Laura
 */
public class FOAJena {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        LogCtl.setCmdLogging(); //avoid loging warnings

        String filePath;
        if(args.length == 2)            
            filePath = args[1];
        else
            filePath = "juan.xml";
        
        Model model = ModelFactory.createDefaultModel();
        model.read(filePath);
        
        model.write(System.out);
        System.out.println("<---------------NTRIPLES---------------->");
        RDFDataMgr.write(System.out,model,RDFFormat.NTRIPLES);
        
        System.out.println("<----------------TURTLE----------------->");
        RDFDataMgr.write(System.out,model,RDFFormat.TURTLE_PRETTY);
        
        System.out.println("<------END------>");
        
        printKnownPeople(model, true);
    }
    
    
    private static void printKnownPeople(Model m, boolean foaf){
        
        StmtIterator it = m.listStatements();
        
        Statement stmt,emptyNodeStmt;
        while(it.hasNext()){
            stmt = it.nextStatement();
            if(!stmt.getPredicate().toString().contains("knows")) continue;
            else{
                System.out.println(stmt.getSubject().toString());
                StmtIterator voidEmptyNodes = m.listStatements( stmt.getObject().asResource(), null,(RDFNode) null);
                while(voidEmptyNodes.hasNext()){
                    
                    emptyNodeStmt = voidEmptyNodes.next();
                    if(!emptyNodeStmt.getPredicate().toString().contains("name"))
                        continue;
                    else{
                        System.out.println("Knows " + emptyNodeStmt.getObject().asLiteral().toString());
                        if(foaf){
                            printKnownPeople(
                                ModelFactory.createDefaultModel().read(
                                   emptyNodeStmt.getObject().asLiteral().toString().toLowerCase() + ".xml"
                                ),false);
                        }
                    }
                }
            }
        }
    }
    
}
