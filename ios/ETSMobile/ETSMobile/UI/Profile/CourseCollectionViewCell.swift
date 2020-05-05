//
//  CourseCollectionViewCell.swift
//  ETSMobile
//
//  Created by Emmanuel Proulx on 2019-10-12.
//  Copyright © 2019 ApplETS. All rights reserved.
//

import UIKit

class CourseCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var gradeTitle: UILabel!
    @IBOutlet weak var courseTitle: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        gradeTitle.textColor = UIColor.black
        gradeTitle.font = UIFont.boldSystemFont(ofSize: 19)
        courseTitle.textColor = UIColor.black
        
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func roundCorners() {
        self.contentView.layer.cornerRadius = 12.0
        self.contentView.layer.masksToBounds = true
        self.contentView.layer.borderWidth = 1.0
        self.contentView.layer.borderColor = UIColor.black.withAlphaComponent(0.25).cgColor
    }
    
    func setCellShadows() {
        layer.masksToBounds = false
        layer.shadowOpacity = 0.23
        layer.shadowRadius = 4
        layer.shadowOffset = CGSize(width: 0, height: 0)
        layer.shadowColor = UIColor.black.cgColor
    }
    
    func setBackgroundColor() {
        self.contentView.backgroundColor = UIColor.white
    }
}
