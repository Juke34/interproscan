package uk.ac.ebi.interpro.scan.management.model.implementations.panther;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import uk.ac.ebi.interpro.scan.management.model.StepInstance;
import uk.ac.ebi.interpro.scan.management.model.implementations.RunBinaryStep;
import uk.ac.ebi.interpro.scan.util.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs the PantherScore on the domtbloutput file provided .
 *
 * @author Gift Nuka
 * @version $Id$
 * @since 1.0
 */

public class RunPantherScoreStep extends RunBinaryStep {

    private static final Logger LOGGER = LogManager.getLogger(RunPantherScoreStep.class.getName());

    private String fastaFileNameTemplate;

    private String fullPathToBinary;

    private String fullPathToHmmScanBinary;

    private String fullPathToPython;

    private String inputFileNameDomTbloutTemplate;

    private String inputFilePantherFamilyNames;

    private String pantherModelsPath;

    private String pantherSingletonFamilyNames;

    private String outputOneFileNameTemplate;

    private boolean forceHmmsearch = true;

    public String getFastaFileNameTemplate() {
        return fastaFileNameTemplate;
    }

    public void setFastaFileNameTemplate(String fastaFileNameTemplate) {
        this.fastaFileNameTemplate = fastaFileNameTemplate;
    }

    @Required
    public void setFullPathToBinary(String fullPathToBinary) {
        this.fullPathToBinary = fullPathToBinary;
    }

    public void setFullPathToHmmScanBinary(String fullPathToHmmScanBinary) {
        this.fullPathToHmmScanBinary = fullPathToHmmScanBinary;
    }

    public String getInputFileNameDomTbloutTemplate() {
        return inputFileNameDomTbloutTemplate;
    }

    @Required
    public void setInputFileNameDomTbloutTemplate(String inputFileNameDomTbloutTemplate) {
        this.inputFileNameDomTbloutTemplate = inputFileNameDomTbloutTemplate;
    }

    public String getInputFilePantherFamilyNames() {
        return inputFilePantherFamilyNames;
    }

    public void setInputFilePantherFamilyNames(String inputFilePantherFamilyNames) {
        this.inputFilePantherFamilyNames = inputFilePantherFamilyNames;
    }

    public void setPantherModelsPath(String pantherModelsPath) {
        this.pantherModelsPath = pantherModelsPath;
    }

    public void setPantherSingletonFamilyNames(String pantherSingletonFamilyNames) {
        this.pantherSingletonFamilyNames = pantherSingletonFamilyNames;
    }

    public void setOutputOneFileNameTemplate(String outputOneFileNameTemplate) {
        this.outputOneFileNameTemplate = outputOneFileNameTemplate;
    }

    public String getFullPathToPython() {
        return fullPathToPython;
    }

    public void setFullPathToPython(String fullPathToPython) {
        this.fullPathToPython = fullPathToPython;
    }

    public boolean isForceHmmsearch() {
        return forceHmmsearch;
    }

    public void setForceHmmsearch(boolean forceHmmsearch) {
        this.forceHmmsearch = forceHmmsearch;
    }

    @Override
    protected List<String> createCommand(StepInstance stepInstance, String temporaryFileDirectory) {
        final List<String> command = new ArrayList<String>();

        final String fastaFilePathName = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.fastaFileNameTemplate);

        final String outputFilePathName = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.getOutputFileNameTemplate());

        final String inputFileNameDomTblout = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.getInputFileNameDomTbloutTemplate());

        //final String  outputTwoFileNameTemplate = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.outputOneFileNameTemplate);

        //final String  pantherModelsPath = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.pantherModelsPath);

        //final String  pantherSingletonFamilyNames = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.pantherSingletonFamilyNames);
        //final String  pantherModelsPath = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.pantherModelsPath);

        //final String inputFilePantherFamilyNames = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.getInputFilePantherFamilyNames());

        //-d output/test.hmmscan.domtbl.out -i input/test_all_appl.fasta
        // -p data/panther/15.2 -n data/panther/15.2/names.tab -m hmmscan
        // -s data/panther/15.2/panther_sngl_fam_names -o output/test_new_panther
        // -e 0.00000001 -i input/test_all_appl.fasta

        command.add(fullPathToPython);
        command.add(fullPathToBinary);

        command.add("-c");
        command.add(fullPathToHmmScanBinary);

        command.add("-p");
        command.add(pantherModelsPath);

        command.add("-n");
        command.add(inputFilePantherFamilyNames);

        command.add("-s");
        command.add(pantherSingletonFamilyNames);

        command.add("-i");
        command.add(fastaFilePathName);

        command.add("-d");
        command.add(inputFileNameDomTblout);


//         output file option
//        if(this.isUsesFileOutputSwitch()){
//            command.add("-o");
//            command.add(outputFilePathName);
//        }

        command.add("-o");
        command.add(outputFilePathName);

        command.add("-m");
        if (forceHmmsearch || Utilities.getSequenceCount() > 10) {
            command.add("hmmsearch");
        }else{
            command.add("hmmscan");
        }

        command.addAll(this.getBinarySwitchesAsList());

        Utilities.verboseLog(1100, "binary cmd to run: " + command.toString());
        return command;

    }

}
