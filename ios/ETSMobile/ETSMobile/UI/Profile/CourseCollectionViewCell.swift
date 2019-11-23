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
        roundCorners()
        setBackgroundColor()
        setCellShadows()
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
        self.contentView.layer.borderColor = UIColor.clear.cgColor
    }
    
    func setCellShadows(){
        self.layer.shadowColor = UIColor.white.cgColor
        self.layer.shadowOffset = CGSize(width: 0, height: 1)
        self.layer.shadowOpacity = 0.5
        self.layer.shadowRadius = 1.0
        self.layer.masksToBounds = false
        self.layer.cornerRadius = 3
        self.clipsToBounds = false
    }
    
    func setBackgroundColor() {
        self.contentView.backgroundColor = UIColor.white
    }
}
