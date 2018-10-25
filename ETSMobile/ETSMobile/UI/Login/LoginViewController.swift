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

    @IBOutlet weak var username: UITextField!
    @IBOutlet weak var password: UITextField!
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
        loginButtonContent!.setTitle(NSLocalizedString("login", comment: "Login"), for: UIControl.State.normal)
    }

    @objc private func hideKeyboard() {
        self.view.endEditing(true)
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
