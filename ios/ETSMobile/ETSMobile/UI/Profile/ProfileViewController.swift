//
//  ProfileViewController.swift
//  ETSMobile
//
//  Created by Emmanuel Proulx on 2019-10-11.
//  Copyright © 2019 ApplETS. All rights reserved.
//

// swiftlint:disable force_cast

import UIKit

class ProfileViewController: UIViewController, UITableViewDataSource {
    
    @IBOutlet var semesterTableView: UITableView!
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 10
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "semesterCell", for: indexPath) as! SemesterTableViewCell
        cell.setCollectionViewDataSourceDelegate(dataSourceDelegate: self, forRow: indexPath.row)
        let flowLayout = UICollectionViewFlowLayout()
        flowLayout.scrollDirection = .vertical
        flowLayout.itemSize = CGSize(width: 75, height: 75)
        flowLayout.minimumInteritemSpacing = 5.0
        cell.coursesCollectionView.collectionViewLayout = flowLayout
        return cell
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return "Automne 2018"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        semesterTableView.dataSource = self
        self.navigationItem.rightBarButtonItem = UIBarButtonItem.init(title: "Mes infos", style: .done, target: self, action: nil)
        // Do any additional setup after loading the view.
        semesterTableView.rowHeight = 200
        semesterTableView.estimatedRowHeight = 200
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

extension ProfileViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        4
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "courseCell", for: indexPath) as! CourseCollectionViewCell
        cell.courseTitle.text = "LOG121"
        cell.gradeTitle.text = "90%"
        return cell
    }
}
