//
//  LoginViewController.swift
//  ETS-mobile
//
//  Created by Emmanuel Proulx on 2018-07-29.
//  Copyright © 2018 applETS. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    @IBAction func sendLoginInfo(_ sender: Any) {

    }

    @IBOutlet weak var madeBy: ETSLabel!
    @IBOutlet weak var username: ETSTextField!
    @IBOutlet weak var password: ETSTextField!
    @IBOutlet weak var loginButtonContent: ETSButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(
            target: self,
            action: #selector(self.hideKeyboard)
        )
        self.view.addGestureRecognizer(tap)
        username!.placeholder = NSLocalizedString("username", comment: "Access code")
        password!.placeholder = NSLocalizedString("password", comment: "Password")
        let rightSideButton = UIButton(type: .custom)
        
        rightSideButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: -16, bottom: 0, right: 0)
        rightSideButton.frame = CGRect(x: CGFloat(username!.frame.size.width - 25), y: CGFloat(5), width: CGFloat(25), height: CGFloat(25))
        let disclosure = UITableViewCell()
        disclosure.frame = rightSideButton.bounds
        disclosure.accessoryType = .detailButton
        disclosure.isUserInteractionEnabled = false
        rightSideButton.addSubview(disclosure)
        rightSideButton.addTarget(self, action: #selector(UsernameInfo), for: .touchUpInside)
        username!.rightView = rightSideButton
        username!.rightViewMode = .always
        
        
        madeBy!.text = NSLocalizedString("madeBy", comment: "Réalisé par")
    loginButtonContent!.setTitle(NSLocalizedString("login", comment: "Login"), for: UIControl.State.normal)
    }

    @objc private func hideKeyboard() {
        self.view.endEditing(true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @objc func UsernameInfo(sender: UIButton!) {
        let alert = UIAlertController(title: NSLocalizedString("universalAccessCode", comment: "universalAccessCode"), message: NSLocalizedString("universalAccessCodeInfo", comment: "universalAccessCodeInfo"), preferredStyle: .actionSheet)
        
        alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
        
        self.present(alert, animated: true)
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
