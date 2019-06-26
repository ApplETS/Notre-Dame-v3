//
//  CourseCard.swift
//  ETSMobile
//
//  Created by Emmanuel Proulx on 2019-06-15.
//  Copyright © 2019 ApplETS. All rights reserved.
//

import SwiftUI

struct CourseCard : View {
    var course: String
    var grade: String
    var body: some View {
        VStack {
            Text(self.grade)
                .font(.largeTitle)
                .padding(20)
            Text(self.course)
                .padding(2)
                .font(.subheadline)
        }
        .clipShape(RoundedRectangle(cornerRadius: 5))
        .overlay(RoundedRectangle(cornerRadius: 5).stroke(Color.white, lineWidth: 4))
        .shadow(radius: 1)
            .frame(width: 125, height: 100)
    }
}

#if DEBUG
struct CourseCardPreviews : PreviewProvider {
    static var previews: some View {
        CourseCard(course: "LOG240", grade: "79%")
    }
}
#endif
