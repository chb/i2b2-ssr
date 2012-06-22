package edu.chip.carranet.client.service;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.chip.carranet.client.data.*;

import java.util.List;

@RemoteServiceRelativePath("ols")
public interface StudyManagementService extends RemoteService {

    public void createMachine(MachineDTO m);

    public void createStudy(StudyDTO s);

    public void createMachine(String machineId, String locator);

    public void createStudy(String studyId, List<String> machineIds);

    public MachineDTO getMachine(String machineId);

    public StudyDTO getStudy(String studyId);

    public void updateMachine(String machineId, MachineDTO m);

    public void updateStudy(String studyId, StudyDTO s);

    public void deleteMachine(String machineId);

    public void deleteStudy(String studyId);

    public List<MachineStatusDTO> getAllMachineStatuses();

    public List<MachineDTO> getMachines();

    public List<StudyDTO> getStudies();

}
