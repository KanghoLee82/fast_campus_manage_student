package org.fastcampus.student_management.application.student;

import java.util.Optional;
import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.domain.Student;
import org.fastcampus.student_management.repo.StudentRepository;

public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public void saveStudent(StudentInfoDto studentInfoDto) {
    Student student = new Student(studentInfoDto.getName(), studentInfoDto.getAge(), studentInfoDto.getAddress());
    studentRepository.save(student);
  }

  public Student getStudent(String name) {
    return studentRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 학생이 없습니다."));
  }

  public void activateStudent(String name) {
    // TODO: 과제 구현 부분
    Student student = studentRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 학생이 없습니다."));

    if(student.isActivate()){
      System.out.println("해당하는 학생은 이미 활성 상태입니다.");
    } else{
      student.setActivated(true);
      studentRepository.save(student);
    }
  }

  public void deactivateStudent(String name) {
    // TODO: 과제 구현 부분
    Student student = studentRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 학생이 없습니다."));

    if(!student.isActivate()){
      System.out.println("해당하는 학생은 이미 비활성 상태입니다.");
    } else{
      student.setActivated(false);
      studentRepository.save(student);
    }
  }
}
