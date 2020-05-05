//
//  SemesterTableViewCell.swift
//  ETSMobile
//
//  Created by Emmanuel Proulx on 2019-10-12.
//  Copyright © 2019 ApplETS. All rights reserved.
//

import UIKit

class SemesterTableViewCell: UITableViewCell {
    @IBOutlet weak var coursesCollectionView: UICollectionView!
    
    func setCollectionViewDataSourceDelegate(dataSourceDelegate: UICollectionViewDataSource & UICollectionViewDelegate, forRow row: Int) {
        coursesCollectionView.delegate = dataSourceDelegate
        coursesCollectionView.dataSource = dataSourceDelegate
        coursesCollectionView.tag = row
        coursesCollectionView.reloadData()
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }


}
