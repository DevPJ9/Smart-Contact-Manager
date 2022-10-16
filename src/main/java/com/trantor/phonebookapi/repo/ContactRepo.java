package com.trantor.phonebookapi.repo;

import com.trantor.phonebookapi.model.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepo extends JpaRepository<ContactModel,Integer> {

    public List<ContactModel> findByfirstName(String name);
}
