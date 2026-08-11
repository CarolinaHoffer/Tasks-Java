package com.tasks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasks.model.Label;
import com.tasks.model.User;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findByUser(User user);

    Optional<Label> findByIdAndUser(Long id, User user);

    Optional<Label> findByNameAndUser(String name, User user);
}