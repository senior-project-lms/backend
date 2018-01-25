package com.lms.services.interfaces;

public interface CourseService {



}



/*
---------------------------------------------------------------------------------------------------------------------------
Interface: ​CourseService
---------------------------------------------------------------------------------------------------------------------------
function: ​entityToPojo
parameters: ​Course, registeredUser: boolean, userCoursePrivileges: boolean,
gradeTypes: boolean assignments: boolean, announcements: boolean, qaQuestions:
boolean, quizTests: boolean, owner: boolean
returns: ​CoursePojo
---------------------------------------------------------------------------------------------------------------------------
function: ​pojoToEntity
parameters: ​CoursePojo
returns: ​Course
---------------------------------------------------------------------------------------------------------------------------
function: ​getCoursesWithOwner
parameters: ​visible: boolean
returns: ​List<CoursePojo>
---------------------------------------------------------------------------------------------------------------------------
function: ​getCourseForAdmin
parameters: ​publicKey: String
returns: ​List<CoursePojo>
---------------------------------------------------------------------------------------------------------------------------
function: ​save
parameters: ​CoursePojo
returns: ​boolean
---------------------------------------------------------------------------------------------------------------------------
function: ​save
parameters: ​List<CoursePojo>
returns: ​boolean
---------------------------------------------------------------------------------------------------------------------------
function: ​getCourseStatusCounts
parameters:
returns: ​HashMap<String, Integer>
------------------------------------------------------------------------------------------------------------------------*
* */