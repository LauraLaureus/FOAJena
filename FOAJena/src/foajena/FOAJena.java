/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foajena;

import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

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
        if(args.length > 1)
            filePath = args[1];
        else
            filePath = "people.xml";
        
        Model model = ModelFactory.createDefaultModel();
        model.read(filePath);
        
        model.write(System.out);
    }
    
}
