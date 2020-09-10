package uk.ac.ebi.interpro.scan.management.model.implementations.pirsr;

//import org.apache.commons.collections.functors.ExceptionClosure;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Required;
import uk.ac.ebi.interpro.scan.management.model.StepInstance;
import uk.ac.ebi.interpro.scan.management.model.implementations.RunBinaryStep;
import uk.ac.ebi.interpro.scan.util.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs the PIRSR binary on the fasta file provided to the output file provided.
 *
 * @author Gift Nuka
 * @version $Id$
 * @since 1.0
 */

public class RunPIRSRBinaryStep extends RunBinaryStep {

    private static final Logger LOGGER = LogManager.getLogger(RunPIRSRBinaryStep.class.getName());

    private String fullPathToBinary;

    private String pathToDataFolder;

    private String hmmscanPath;

    private String inputFileNameRawOutTemplate;

    private String inputFileNameDomTbloutTemplate;

    private String inputFileNameAlignmentsTemplate;

    private String sitesAnnotationFileName;

    @Required
    public void setFullPathToBinary(String fullPathToBinary) {
        this.fullPathToBinary = fullPathToBinary;
    }

    @Required
    public void setPathToDataFolder(String pathToDataFolder) {
        this.pathToDataFolder = pathToDataFolder;
    }

    public String getHmmscanPath() {
        return hmmscanPath;
    }

    @Required
    public void setHmmerPath(String hmmscanPath) {
        this.hmmscanPath = hmmscanPath;
    }

    public String getInputFileNameRawOutTemplate() {
        return inputFileNameRawOutTemplate;
    }

    @Required
    public void setInputFileNameRawOutTemplate(String inputFileNameRawOutTemplate) {
        this.inputFileNameRawOutTemplate = inputFileNameRawOutTemplate;
    }

    public String getInputFileNameDomTbloutTemplate() {
        return inputFileNameDomTbloutTemplate;
    }

    @Required
    public void setInputFileNameDomTbloutTemplate(String inputFileNameDomTbloutTemplate) {
        this.inputFileNameDomTbloutTemplate = inputFileNameDomTbloutTemplate;
    }

    public String getInputFileNameAlignmentsTemplate() {
        return inputFileNameAlignmentsTemplate;
    }

    @Required
    public void setInputFileNameAlignmentsTemplate(String inputFileNameAlignmentsTemplate) {
        this.inputFileNameAlignmentsTemplate = inputFileNameAlignmentsTemplate;
    }

    public String getSitesAnnotationFileName() {
        return sitesAnnotationFileName;
    }

    @Required
    public void setSitesAnnotationFileName(String sitesAnnotationFileName) {
        this.sitesAnnotationFileName = sitesAnnotationFileName;
    }


    @Override
    protected List<String> createCommand(StepInstance stepInstance, String temporaryFileDirectory) {
        final List<String> command = new ArrayList<String>();
        final String outputFilePathName = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.getOutputFileNameTemplate());

        final String inputFileNameRawOut = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.getInputFileNameRawOutTemplate());
        final String inputFileNameDomTblout = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.getInputFileNameDomTbloutTemplate());

        final String inputFileNameAlignments = stepInstance.buildFullyQualifiedFilePath(temporaryFileDirectory, this.getInputFileNameAlignmentsTemplate());

        command.add(fullPathToBinary);

        command.add("-data");
        command.add(pathToDataFolder);

        // Path to hmmscan binaries
        command.add("-hmmscan");
        command.add(this.getHmmscanPath());


        command.add("-s");
        command.add(sitesAnnotationFileName);

        command.add("-a");
        command.add(inputFileNameAlignments);

        command.add("-O");
        command.add(inputFileNameRawOut);

        command.add("-d");
        command.add(inputFileNameDomTblout);

        // output file option
        if(this.isUsesFileOutputSwitch()){
            command.add("-out");
            command.add(outputFilePathName);
        }

        Utilities.verboseLog(1100, "binary cmd to run: " + command.toString());
        return command;

    }

}
