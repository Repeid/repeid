package org.repeid.services.resources.admin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.repeid.models.enums.BussinessType;
import org.repeid.models.enums.Gender;
import org.repeid.models.enums.PersonType;

public class CommonsResourceImpl implements CommonsResource {

    public CommonsResourceImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<String> getPersonsType() {
        return Arrays.stream(PersonType.values()).map(e -> e.toString()).collect(Collectors.toList());
    }

    @Override
    public List<String> getMaritalStatus() {
        return Arrays.stream(PersonType.values()).map(e -> e.toString()).collect(Collectors.toList());
    }

    @Override
    public List<String> getGenders() {
        return Arrays.stream(Gender.values()).map(e -> e.toString()).collect(Collectors.toList());
    }

    @Override
    public List<String> getBussinessType() {
        return Arrays.stream(BussinessType.values()).map(e -> e.toString()).collect(Collectors.toList());
    }

}
