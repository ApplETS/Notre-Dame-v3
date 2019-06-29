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
    var semester: String
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(self.semester)
                .font(.headline)
                .padding(.leading, 15)
                .padding(.vertical, 5)
            ScrollView(isScrollEnabled: true, showsHorizontalIndicator: false) {
                HStack(alignment: .top, spacing: 0) {
                    ForEach(self.courses.identified(by: \.sigle)) { course in
                        CourseCard(course: course.sigle, grade: course.noteSur100 ?? "0")
                    }
                }
            }

        }

    }
}

#if DEBUG
struct SemesterCoursesPreviews : PreviewProvider {
    static var previews: some View {
        SemesterCourses(courses: [Cours(sigle: "ING150", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "98%", nbCredits: 2, titreCours: "Something"),
            Cours(sigle: "LOG121", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "90%", nbCredits: 2, titreCours: "Something"),
            Cours(sigle: "LOG240", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "67%", nbCredits: 2, titreCours: "Something"),
            Cours(sigle: "COM110", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "76%", nbCredits: 2, titreCours: "Something")],
        semester: "Hiver 2018")
    }
}
#endif
