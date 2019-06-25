//
//  SemesterCourses.swift
//  ETSMobile
//
//  Created by Emmanuel Proulx on 2019-06-17.
//  Copyright © 2019 ApplETS. All rights reserved.
//

import SwiftUI

struct SemesterCourses : View {
    var courses: [Course]
    var body: some View {
        HStack {
            ForEach(self.courses.identified(by: \.courseCode)) { course in
                CourseCard(course: course.courseCode, grade: course.grade)
            }
        }
    }
}

#if DEBUG
struct SemesterCoursesPreviews : PreviewProvider {
    static var previews: some View {
        SemesterCourses(courses: [Course(courseCode: "LOG121", grade: "98%"),
            Course(courseCode: "LOG240", grade: "90%")])
    }
}
#endif
