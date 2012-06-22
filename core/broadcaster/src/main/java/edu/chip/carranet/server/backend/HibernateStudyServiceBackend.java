package edu.chip.carranet.server.backend;


import edu.carranet.client.auth.Token;
import edu.chip.carranet.ExternalExceptions.ResourceAlreadyExistsError;
import edu.chip.carranet.HibernateResourceStore;
import edu.chip.carranet.auth.backend.BackendUtil;
import edu.chip.carranet.auth.backend.TokenUtil;
import edu.chip.carranet.client.data.MachineDTO;
import edu.chip.carranet.client.data.MachineStatusDTO;
import edu.chip.carranet.client.data.StudyDTO;
import edu.chip.carranet.jaxb.*;
import edu.chip.carranet.overlay.persistance.PersistedResource;
import edu.chip.carranet.overlay.persistance.ResourceAlreadyExistsException;
import edu.chip.carranet.overlay.persistance.ResourceStore;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HibernateStudyServiceBackend implements IStudyManagementBackend {


    private ResourceStore rs;

    public void setRs(ResourceStore rs) {
        this.rs = rs;
    }

    @Override
    public void createMachine(Token t, MachineDTO m) {
        BackendUtil.verifyTokenAndAdmin(getId(t));
        Machine machine = new Machine();
        machine.setMachineEntry(new MachineEntry());
        machine.getMachineEntry().setLocator(m.getUri());
        machine.getMachineEntry().setMachineId(m.getMachineName());

        String identifier = "/machines/" + machine.getMachineEntry().getMachineId();
        try {
            rs.create(identifier, new PersistedResource("", CarraDataUtil.toBytes(machine)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ResourceAlreadyExistsException e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public void createStudy(Token t, StudyDTO s) {
        String identifier = "/machines/" + s.getStudyName();
        try {
            Study study = new Study();
            study.setStudyEntry(new StudyEntry());
            study.getStudyEntry().setStudyId(s.getStudyName());
            study.getStudyEntry().getMachineIds().addAll(s.getStudyMachines());
            rs.create(identifier, new PersistedResource("", CarraDataUtil.toBytes(study)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
           catch (ResourceAlreadyExistsException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createMachine(Token t, String machineId, String locator) {
        BackendUtil.verifyTokenAndAdmin(getId(t));
        Machine machine = new Machine();
        machine.setMachineEntry(new MachineEntry());
        machine.getMachineEntry().setLocator(locator);
        machine.getMachineEntry().setMachineId(machineId);

        String identiifier = "/machines/" + machine.getMachineEntry().getMachineId();
        try {
            rs.create(identiifier, new PersistedResource("", CarraDataUtil.toBytes(machine)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
           catch (ResourceAlreadyExistsException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createStudy(Token t, String studyId, List<String> machineIds) {
        try {
            BackendUtil.verifyTokenAndAdmin(getId(t));
            String identifier = "/studies/" + studyId;

            Study s = new Study();
            s.setStudyEntry(new StudyEntry());
            s.getStudyEntry().setStudyId(studyId);
            s.getStudyEntry().getMachineIds().addAll(machineIds);
            rs.create(identifier, new PersistedResource("", CarraDataUtil.toBytes(s)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
           catch (ResourceAlreadyExistsException e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public MachineDTO getMachine(Token t, String machineId) {
        try {
            Machine m = CarraDataUtil.machineFromBytes(rs.read("/machines/" + machineId).getPayload());
            MachineDTO machine = new MachineDTO();
            machine.setMachineName(m.getMachineEntry().getMachineId());
            machine.setUri(m.getMachineEntry().getLocator());
            return machine;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public StudyDTO getStudy(Token t, String studyId) {
        try {
            Study study = CarraDataUtil.studyFromBytes(rs.read("/studies/" + studyId).getPayload());
            StudyDTO dto = new StudyDTO();
            dto.setStudyName(study.getStudyEntry().getStudyId());
            dto.getStudyMachines().addAll(study.getStudyEntry().getMachineIds());
            return dto;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public void updateMachine(Token t, String machineId, MachineDTO updatedMachine) {
        try {
            Machine m = new Machine();
            m.setMachineEntry(new MachineEntry());
            m.getMachineEntry().setMachineId(machineId);
            m.getMachineEntry().setLocator(updatedMachine.getUri());
            rs.update("/machines/" + machineId, new PersistedResource("", CarraDataUtil.toBytes(m)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateStudy(Token t, String studyId, StudyDTO updatedStudy) {
        try {
            Study s = new Study();
            s.setStudyEntry(new StudyEntry());
            s.getStudyEntry().setStudyId(studyId);
            s.getStudyEntry().getMachineIds().addAll(updatedStudy.getStudyMachines());
            rs.update("/studies/" + studyId, new PersistedResource("", CarraDataUtil.toBytes(s)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }    }

    @Override
    public void deleteMachine(Token t, String machineId) {
        rs.delete("/machines/" + machineId);
    }

    @Override
    public void deleteStudy(Token t, String studyId) {
        rs.delete("/studies/" + studyId);
    }

    @Override
    public ArrayList<MachineStatusDTO> getAllMachineStatuses(Token t) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<MachineDTO> getMachines(Token t) {
        try {

            List<MachineDTO> returnList = new ArrayList<MachineDTO>();

            for (PersistedResource rez : rs.getAll("/machines/")) {

                Machine m = CarraDataUtil.machineFromBytes(rez.getPayload());
                MachineDTO machine = new MachineDTO();
                machine.setMachineName(m.getMachineEntry().getMachineId());
                machine.setUri(m.getMachineEntry().getLocator());
                returnList.add(machine);
            }
            return returnList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public List<StudyDTO> getStudies(Token t) {
        try {
            List<StudyDTO> returnList = new ArrayList<StudyDTO>();
            for (PersistedResource rez : rs.getAll("/studies/")) {

                Study s = CarraDataUtil.studyFromBytes(rez.getPayload());
                StudyDTO dto = new StudyDTO();
                dto.setStudyName(s.getStudyEntry().getStudyId());
                dto.getStudyMachines().addAll(s.getStudyEntry().getMachineIds());
                returnList.add(dto);
            }
            return returnList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        }    }

    private Identity getId(Token t) {
        return TokenUtil.authStringToIdentity(t.getAuthString());
    }

}
