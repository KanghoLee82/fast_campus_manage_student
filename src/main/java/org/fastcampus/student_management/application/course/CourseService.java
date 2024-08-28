package org.fastcampus.student_management.application.course;

import java.util.ArrayList;
import java.util.List;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.domain.Course;
import org.fastcampus.student_management.domain.DayOfWeek;
import org.fastcampus.student_management.domain.Student;
import org.fastcampus.student_management.repo.CourseRepository;

public class CourseService {
  private final CourseRepository courseRepository;
  private final StudentService studentService;

  public CourseService(CourseRepository courseRepository, StudentService studentService) {
    this.courseRepository = courseRepository;
    this.studentService = studentService;
  }

  public void registerCourse(CourseInfoDto courseInfoDto) {
    Student student = studentService.getStudent(courseInfoDto.getStudentName());
    Course course = new Course(student, courseInfoDto.getCourseName(), courseInfoDto.getFee(), courseInfoDto.getDayOfWeek(), courseInfoDto.getCourseTime());
    courseRepository.save(course);
  }

  public List<CourseInfoDto> getCourseDayOfWeek(DayOfWeek dayOfWeek) {
    // TODO: 과제 구현 부분
    List<CourseInfoDto> courseInfoDtoList = new ArrayList<CourseInfoDto>();
    List<Course> courseList = courseRepository.getCourseDayOfWeek(dayOfWeek);
    for(Course course : courseList){
      // 학생 정보 우선 가져오기
      Student student = studentService.getStudent(course.getStudentName());

      // 학생 활동 상태 Check
      if(!student.isActivate()) continue;

      CourseInfoDto dto
          = new CourseInfoDto(course.getCourseName()
                            , course.getFee()
                            , course.getDayOfWeek().name()
                            , course.getStudentName()
                            , course.getCourseTime());
      courseInfoDtoList.add(dto);
    }
    return courseInfoDtoList;
  }

  public void changeFee(String studentName, int fee) {
    // TODO: 과제 구현 부분
    List<Course> courseList = courseRepository.getCourseListByStudent(studentName);
    for (Course course : courseList) {
      course.setFee(fee);
    }
    courseRepository.saveCourses(courseList);
  }
}
