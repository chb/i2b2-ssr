package edu.chip.carranet.server;

import edu.carranet.client.auth.Token;
import edu.chip.carranet.client.data.*;
import edu.chip.carranet.client.service.StudyManagementService;
import edu.chip.carranet.server.backend.IStudyManagementBackend;
import edu.chip.carranet.server.backend.MockStudyManagementBackend;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class StudyManagementServiceImpl extends CarraGwtService
        implements StudyManagementService {

    @Autowired
    IStudyManagementBackend backend;

    public void setBackend(IStudyManagementBackend backend) {
        this.backend = backend;
    }

    @Override
    public void createMachine(MachineDTO m) {
        Token t = getAuthToken();
        if (t != null) {
            backend.createMachine(t, m);
            return;
        }
        throw new RuntimeException("Permission Denied");
    }

    @Override
    public void createStudy(StudyDTO s) {
        Token t = getAuthToken();
        if (t != null) {
            backend.createStudy(t, s);
            return;
        }
        throw new RuntimeException("Permission Denied");
    }



    @Override
    public void createMachine(String machineId, String locator) {
        Token t = getAuthToken();
        if (t != null) {
            backend.createMachine(t, machineId, locator);
            return;
        }
        throw new RuntimeException("Permission Denied");

    }

    @Override
    public void createStudy(String studyId, List<String> machineIds) {
        Token t = getAuthToken();
        if (t != null) {
            backend.createStudy(t, studyId, machineIds);
            return;
        }
        throw new RuntimeException("Permission Denied");
    }



    @Override
    public MachineDTO getMachine(String machineId) {
        Token t = getAuthToken();
        if (t != null) {
            return backend.getMachine(t, machineId);
        }
        throw new RuntimeException("Permission Denied");
    }

    @Override
    public StudyDTO getStudy(String studyId) {
        Token t = getAuthToken();

        if (t != null) {
            return backend.getStudy(t, studyId);
        }
        throw new RuntimeException("Permission Denied");
    }



    /**
     * @param machineId
     * @param updatedMachine
     * @return
     */
    @Override
    public void updateMachine(String machineId, MachineDTO updatedMachine) {
        Token t = getAuthToken();
        if (t != null) {
            backend.updateMachine(t, machineId, updatedMachine);
            return;
        }
        throw new RuntimeException("Permission Denied");
    }

    /**
     * @param studyId
     * @param updatedStudy
     * @return
     */
    @Override
    public void updateStudy(String studyId, StudyDTO updatedStudy) {
        Token t = getAuthToken();
        if (t != null) {
            backend.updateStudy(t, studyId, updatedStudy);
            return;
        }
        throw new RuntimeException("Permission Denied");
    }

 

    @Override
    public void deleteMachine(String machineId) {
        Token t = getAuthToken();
        if (t != null) {
            backend.deleteMachine(t, machineId);
            return;
        }
        throw new RuntimeException("Permission Denied");
    }

    @Override
    public void deleteStudy(String studyId) {
        Token t = getAuthToken();
        if (t != null) {
            backend.deleteStudy(t, studyId);
            return;
        }
        throw new RuntimeException("Permission Denied");

    }


    @Override
    public ArrayList<MachineStatusDTO> getAllMachineStatuses() {
        Token t = getAuthToken();
        if (t != null) {
            return backend.getAllMachineStatuses(t);
        }
        throw new RuntimeException("Permission Denied");

    }

    @Override
    public List<MachineDTO> getMachines() {
        Token t = getAuthToken();
        if (t != null) {
            return backend.getMachines(t);
        }

        throw new RuntimeException("Permission Denied");
    }

    @Override
    public List<StudyDTO> getStudies() {
        Token t = getAuthToken();
        if (t != null) {
            return backend.getStudies(t);
        }

        throw new RuntimeException("Permission Denied");
    }



}
