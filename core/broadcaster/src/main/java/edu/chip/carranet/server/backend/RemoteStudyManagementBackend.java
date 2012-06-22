package edu.chip.carranet.server.backend;

import edu.carranet.client.auth.Token;
import edu.carranet.client.overlay.OLSClient;
import edu.carranet.client.overlay.OLSCredentials;
import edu.carranet.client.overlay.OLSException;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.data.MachineStatusDTO;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.client.data.StudyUserDTO;
import edu.chip.carranet.jaxb.*;
import edu.chip.carranet.server.CarraGwtService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class RemoteStudyManagementBackend extends CarraGwtService implements IStudyManagementBackend {

    private OLSClient olsClient;

    public RemoteStudyManagementBackend(String olsUrl) throws MalformedURLException {
        olsClient = new OLSClient(olsUrl);


    }

    @Override
    public void createMachine(Token t, MachineDTO m) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            olsClient.createMachine(creds, makeDecoratedMachine(m));
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void createStudy(Token t, StudyDTO s) {
        OLSCredentials creds = getOLSCreds(t);

        try {
            olsClient.createStudy(creds, makeDecoratedStudy(s));
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }


    @Override
    public void createMachine(Token t, String machineId, String locator) {
        OLSCredentials creds = getOLSCreds(t);

        try {
            olsClient.createMachine(creds, machineId, locator);
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void createStudy(Token t, String studyId, List<String> machines) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            olsClient.createStudy(creds, studyId, machines);

        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }


    @Override
    public MachineDTO getMachine(Token t, String machineId) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            Machine m = olsClient.getMachine(creds, machineId);
            MachineDTO returnMachine = new MachineDTO();
            returnMachine.setMachineName(m.getMachineEntry().getMachineId());
            returnMachine.setUri(m.getMachineEntry().getLocator());
            return returnMachine;
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public StudyDTO getStudy(Token t, String studyId) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            Study s = olsClient.getStudy(creds, studyId);
            StudyDTO returnStudy = new StudyDTO();
            returnStudy.setStudyName(s.getStudyEntry().getStudyId());
            returnStudy.setStudyMachines(s.getStudyEntry().getMachineIds());
            return returnStudy;

        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }

    }


    @Override
    public void updateMachine(Token t, String machineId, MachineDTO updatedMachine) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            Machine m = new Machine();
            MachineEntry entry = new MachineEntry();
            m.setMachineEntry(entry);
            entry.setMachineId(updatedMachine.getMachineName());
            entry.setLocator(updatedMachine.getUri());

            olsClient.updateMachine(creds, machineId, m);
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void updateStudy(Token t, String studyId, StudyDTO updatedStudy) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            Study s = new Study();
            StudyEntry entry = new StudyEntry();
            s.setStudyEntry(entry);
            entry.setStudyId(updatedStudy.getStudyName());
            entry.getMachineIds().addAll(updatedStudy.getStudyMachines());


            olsClient.updateStudy(creds, studyId, s);
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }


    @Override
    public void deleteMachine(Token t, String machineId) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            olsClient.deleteMachine(creds, machineId);
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void deleteStudy(Token t, String studyId) {
        OLSCredentials creds = getOLSCreds(t);
        try {
            olsClient.deleteStudy(creds, studyId);
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
    }


    @Override
    public ArrayList<MachineStatusDTO> getAllMachineStatuses(Token t) {
        final ArrayList<MachineStatusDTO> status = new ArrayList<MachineStatusDTO>();
        final List<MachineDTO> machines = getMachines(t);
        PingThread[] threads = new PingThread[machines.size()];
        for (int i = 0; i < threads.length; i++) {

            threads[i] = new PingThread(machines.get(i));
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignore) {
            }
        }

        for (int i = 0; i < threads.length; i++) {
            MachineStatusDTO temp = new MachineStatusDTO();
            temp.setMachine(threads[i].getDto());

            temp.setStatus(threads[i].getStatus());
            status.add(temp);
        }


        return status;
    }


    @Override
    public List<MachineDTO> getMachines(Token t) {
        OLSCredentials creds = getOLSCreds(t);
        List<MachineDTO> returnList = new ArrayList<MachineDTO>();
        try {
            Machines machines = olsClient.listMachines(creds);
            for (MachineEntry m : machines.getMachineList()) {
                returnList.add(new MachineDTO(m.getMachineId(), m.getLocator()));
            }
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
        return returnList;
    }

    @Override
    public List<StudyDTO> getStudies(Token t) {
        List<StudyDTO> returnList = new ArrayList<StudyDTO>();
        OLSCredentials creds = getOLSCreds(t);

        try {
            Studies studies = olsClient.listStudies(creds);
            for (StudyEntry se : studies.getStudyList()) {
                returnList.add(new StudyDTO(se.getStudyId(), se.getMachineIds()));
            }
        } catch (OLSException e) {
            throw new RuntimeException("Error");
        }
        return returnList;

    }


    private OLSCredentials getOLSCreds(Token t) {
        String authString = null;
        if (t != null) {
            authString = t.getAuthString();
        }
        return new OLSCredentials(authString);
    }


    private static Machine makeDecoratedMachine(MachineDTO dto) {
        Machine m = null;
        if (dto != null) {
            m = new Machine();
            MachineEntry me = new MachineEntry();
            me.setMachineId(dto.getMachineName());
            me.setLocator(dto.getUri());
            m.setMachineEntry(me);
        }

        return m;
    }

    private static Study makeDecoratedStudy(StudyDTO dto) {
        Study s = null;
        if (dto != null) {
            s = new Study();
            StudyEntry se = new StudyEntry();
            se.setStudyId(dto.getStudyName());
            for (String machineId : dto.getStudyMachines()) {
                se.getMachineIds().add(machineId);
            }
            s.setStudyEntry(se);
        }
        return s;
    }

    private static User makeDecoratedUser(StudyUserDTO dto) {
        User u = null;

        if (dto != null) {
            u = new User();
            UserEntry ue = new UserEntry();
            ue.setUserId(dto.getUserName());
            for (String studyId : dto.getStudyIds()) {
                ue.getStudyIds().add(studyId);
            }
            u.setUserEntry(ue);
        }
        return u;
    }

}
