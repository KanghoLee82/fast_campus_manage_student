package org.fastcampus.student_management;

import org.fastcampus.student_management.application.course.CourseService;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.repo.CourseRepository;
import org.fastcampus.student_management.repo.StudentRepository;
import org.fastcampus.student_management.ui.course.CourseController;
import org.fastcampus.student_management.ui.course.CoursePresenter;
import org.fastcampus.student_management.ui.student.StudentController;
import org.fastcampus.student_management.ui.student.StudentPresenter;
import org.fastcampus.student_management.ui.UserInputType;

public class Main {

  public static void main(String[] args) {
    StudentRepository studentRepository = new StudentRepository();
    CourseRepository courseRepository = new CourseRepository();

    StudentService studentService = new StudentService(studentRepository);
    CourseService courseService = new CourseService(courseRepository, studentService);

    CoursePresenter coursePresenter = new CoursePresenter();
    StudentPresenter studentPresenter = new StudentPresenter();

    CourseController courseController = new CourseController(coursePresenter, courseService, studentPresenter);
    StudentController studentController = new StudentController(studentPresenter, studentService);

    StudentInfoDto studentInfoDto1 = new StudentInfoDto("홍길동", 20, "서울시 강남구");
    StudentInfoDto studentInfoDto2 = new StudentInfoDto("동길동", 29, "서울시 노원구");
    studentService.saveStudent(studentInfoDto1);
    studentService.saveStudent(studentInfoDto2);

    CourseInfoDto courseInfoDto1 = new CourseInfoDto("바이올린", 1000, "MONDAY", "홍길동", 1717299008L);
    CourseInfoDto courseInfoDto2 = new CourseInfoDto("첼로", 1200, "TUESDAY", "동길동", 1717299008L);
    courseService.registerCourse(courseInfoDto1);
    courseService.registerCourse(courseInfoDto2);

    studentPresenter.showMenu();
    UserInputType userInputType = studentController.getUserInput();
    while (userInputType != UserInputType.EXIT) {
      switch (userInputType) {
        case NEW_STUDENT: //학생 추가
          studentController.registerStudent();
          break;
        case NEW_COURSE:  //수업 등록
          courseController.registerCourse();
          break;
        case SHOW_COURSE_DAY_OF_WEEK: //요일별 수업 조회
          courseController.showCourseDayOfWeek();
          break;
        case ACTIVATE_STUDENT:  //학생 활성화
          studentController.activateStudent();
          break;
        case DEACTIVATE_STUDENT:  //학생 비활성화
          studentController.deactivateStudent();
          break;
        case CHANGE_FEE:  //수강료 변경
          courseController.changeFee();
          break;
        default:
          studentPresenter.showErrorMessage();
          break;
      }
      studentPresenter.showMenu();
      userInputType = studentController.getUserInput();
    }
  }
}