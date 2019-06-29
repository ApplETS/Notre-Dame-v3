//
//  ProfileViewController.swift
//  ETS-mobile
//
//  Created by Emmanuel on 2018-04-21.
//  Copyright © 2018 applETS. All rights reserved.
//

import UIKit
import SwiftUI
import ETSKit

class ProfileViewController: UIViewController {
    
    // swiftlint:disable all
    var childHostingController = UIHostingController(rootView: SemesterCourses(courses: [Cours(sigle: "ING150", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "98%", nbCredits: 2, titreCours: "Something"),Cours(sigle: "LOG121", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "90%", nbCredits: 2, titreCours: "Something"),Cours(sigle: "LOG240", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "67%", nbCredits: 2, titreCours: "Something"),Cours(sigle: "COM110", groupe: "08", session: "Something", programmeEtudes: "SOMETHING", cote: "", noteSur100: "76%", nbCredits: 2, titreCours: "Something")], semester: "Hiver 2018"))

    // swiftlint: enable all
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        childHostingController.view.translatesAutoresizingMaskIntoConstraints = false
        childHostingController.view.frame = self.view.bounds
        self.view.addSubview(childHostingController.view)
        self.addChild(childHostingController)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
