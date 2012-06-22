package edu.chip.carranet.server.backend;

import edu.carranet.client.auth.Token;
import edu.chip.carranet.client.data.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface that describes a study util
 */
public interface IStudyManagementBackend {

    /**
     * Create a machine
     * @param m
     * @return
     */
    void createMachine(Token t, MachineDTO m);

    /**
     * Creates a new study
     * @param s
     * @return
     */
    void createStudy(Token t, StudyDTO s);



    /**
     * Adds a new machine
     * @param t
     * @param machineId  Id of the machine
     * @param locator    address of the machine
     * @return
     */
    void createMachine(Token t, String machineId, String locator);


    /**
     * Creates a new study
     * @param t
     * @param studyId
     * @return
     */
    void createStudy(Token t, String studyId, List<String> machineIds);




    /**
     *
     * @param machineId
     * @return
     */
    MachineDTO getMachine(Token t, String machineId);

    /**
     *
     * @param studyId
     * @return
     */
    StudyDTO getStudy(Token t, String studyId);



    /**
     *
     * @param machineId
     * @param updatedMachine
     * @return
     */
    void updateMachine(Token t, String machineId, MachineDTO updatedMachine);

    /**
     *
     * @param studyId
     * @param updatedStudy
     * @return
     */
    void updateStudy(Token t, String studyId, StudyDTO updatedStudy);


    /**
     *
     * @param machineId
     * @return
     */
    void deleteMachine(Token t, String machineId);

    /**
     *
     * @param studyId
     * @return
     */
    void deleteStudy(Token t, String studyId);



    /**
     *
     * @return
     */
    ArrayList<MachineStatusDTO> getAllMachineStatuses(Token t);

    /**
     *
     * @return
     */
    List<MachineDTO> getMachines(Token t);

    /**
     *
     * @return
     */
    List<StudyDTO> getStudies(Token t);
}
