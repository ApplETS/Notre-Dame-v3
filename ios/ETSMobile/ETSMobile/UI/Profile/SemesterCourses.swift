//
//  SemesterCourses.swift
//  ETSMobile
//
//  Created by Emmanuel Proulx on 2019-06-17.
//  Copyright © 2019 ApplETS. All rights reserved.
//

import SwiftUI
import ETSKit

struct SemesterCourses : View {
    var courses: [Cours]
    var body: some View {
        HStack {
            ForEach(self.courses.identified(by: \.sigle)) { course in
                CourseCard(course: course.sigle, grade: course.noteSur100 ?? "0")
            }
        }
    }
}

#if DEBUG
struct SemesterCoursesPreviews : PreviewProvider {
    static var previews: some View {
        SemesterCourses(courses: [Cours(sigle: "ING150", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "98%", nbCredits: 2, titreCours: "Something")])
    }
}
#endif
