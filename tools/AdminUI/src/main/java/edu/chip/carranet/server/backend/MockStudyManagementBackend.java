package edu.chip.carranet.server.backend;

import edu.carranet.client.auth.Token;
import edu.chip.carranet.client.data.*;

import java.util.*;


public class MockStudyManagementBackend implements IStudyManagementBackend {


    static List<StudyDTO> studyList = new ArrayList<StudyDTO>();
    static ArrayList<MachineDTO> machineList = new ArrayList<MachineDTO>();
    static List<StudyUserDTO> userList = new ArrayList<StudyUserDTO>();

    static {
        final MachineDTO machine1 = new MachineDTO();
        machine1.setUri("harvard-node.tch.harvard.edu");
        machine1.setMachineName("Harvard");
        machineList.add(machine1);

        final MachineDTO machine2 = new MachineDTO();
        machine2.setUri("duke-node.tch.harvard.edu");
        machine2.setMachineName("Duke");
        machineList.add(machine2);

        final MachineDTO machine3 = new MachineDTO();
        machine3.setUri("cinci-node.tch.harvard.edu");
        machine3.setMachineName("CC");
        machineList.add(machine3);

        final MachineDTO machine4 = new MachineDTO();
        machine4.setUri("princeton-medical.tch.harvard.edu");
        machine4.setMachineName("Princeton");
        machineList.add(machine4);

        StudyDTO study1 = new StudyDTO();
        study1.setStudyName("Diabetes");
        study1.getStudyMachines().addAll(
                Arrays.asList(new String[]{"Harvard", "Duke"}));
        studyList.add(study1);

        StudyDTO study2 = new StudyDTO();
        study2.setStudyName("IBD");
        study2.getStudyMachines().addAll(
                Arrays.asList(new String[]{"CC", "Princeton"}));
        studyList.add(study2);

        StudyDTO study3 = new StudyDTO();
        study3.setStudyName("CARRA and friends PediRum study");
        study3.getStudyMachines().addAll(
                Arrays.asList(new String[]{"Harvard", "Duke", "CC", "Princeton"}));
        studyList.add(study3);

        
    }

    @Override
    public void createMachine(Token t, MachineDTO m) {
        if(!machineList.contains(m)) {
            machineList.add(m);
        }
    }

    @Override
    public void createStudy(Token t, StudyDTO s) {
        if(!studyList.contains(s)) {
            studyList.add(s);
        }
    }



    @Override
    public void createMachine(Token t, String machineId, String locator) {
        MachineDTO m = new MachineDTO(machineId, locator);
        machineList.add(m);
    }

    @Override
    public void createStudy(Token t, String studyId, List<String> machines) {
        StudyDTO study = new StudyDTO(studyId, machines);
        if(!studyList.contains(study)) {
            studyList.add(study);
        }
    }


    @Override
    public MachineDTO getMachine(Token t, String machineId) {
        for (MachineDTO m : machineList) {
            if (m.getMachineName().equals(machineId)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public StudyDTO getStudy(Token t, String studyId) {
        for (StudyDTO s : studyList) {
            if (s.getStudyName().equals(studyId)) {
                return s;
            }
        }
        return null;
    }



    /**
     * TODO: fix that nasty return type.  it makes no sense.
     *
     * @param machineId
     * @param updatedMachine
     * @return
     */
    @Override
    public void updateMachine(Token t, String machineId, MachineDTO updatedMachine) {
        ListIterator<MachineDTO> iter = machineList.listIterator();
        while (iter.hasNext()) {
            MachineDTO m = iter.next();
            if (m.getMachineName().equals(machineId)) {
                iter.set(updatedMachine);
            }
        }
    }

    /**
     * TODO: What a hideous contract, to return the study that we updated rather than the old value.  This has a
     * TODO: lot to do with the way this interface gets used as a callback.
     *
     * @param studyId
     * @param updatedStudy
     * @return
     */
    @Override
    public void updateStudy(Token t, String studyId, StudyDTO updatedStudy) {
        ListIterator<StudyDTO> iter = studyList.listIterator();
        while (iter.hasNext()) {
            StudyDTO s = iter.next();
            if (s.getStudyName().equals(studyId)) {
                iter.set(updatedStudy);
            }
        }
    }



    @Override
    public void deleteMachine(Token t, String machineId) {
        Iterator<MachineDTO> i = machineList.iterator();

        while (i.hasNext()) {
            MachineDTO m = i.next();
            if (m.getMachineName().equals(machineId)) {
                i.remove();
            }
        }
    }

    @Override
    public void deleteStudy(Token t, String studyId) {
        Iterator<StudyDTO> i = studyList.iterator();
        while (i.hasNext()) {
            StudyDTO s = i.next();
            if (s.getStudyName().equals(studyId)) {
                i.remove();
            }
        }
    }


    @Override
    public ArrayList<MachineStatusDTO> getAllMachineStatuses(Token t) {
        ArrayList<MachineStatusDTO> status = new ArrayList<MachineStatusDTO>();
        Random r = new Random(new Date().getTime());
        for (MachineDTO m : machineList) {


            int random = r.nextInt();
            if (random % 2 == 0) {

                MachineStatusDTO m1 = new MachineStatusDTO(m, MachineStatusEnum.Responding);
                status.add(m1);
            } else {
                MachineStatusDTO m1 = new MachineStatusDTO(m, MachineStatusEnum.NotResponding);
                status.add(m1);
            }

        }
        return status;
    }

    @Override
    public List<MachineDTO> getMachines(Token t) {
        return new ArrayList<MachineDTO>(machineList);
    }

    @Override
    public List<StudyDTO> getStudies(Token t) {
        return new ArrayList<StudyDTO>(studyList);
    }



}


