//
//  LoginViewController.swift
//  ETS-mobile
//
//  Created by Emmanuel Proulx on 2018-07-29.
//  Copyright © 2018 applETS. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    @IBOutlet weak var username: UITextField!
    @IBOutlet weak var password: ETSTextField!
    @IBOutlet weak var loginButton: ETSButton!
    @IBOutlet weak var madeBy: ETSLabel!

    let passwordRightSideButton = UIButton(type: .custom)
    var passwordHidden = true
    var isSecureTextEntry = true
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    @IBAction func sendLoginInfo(_ sender: Any) {
        self.loginButton.loading = true
        // just for testing, we would do that after the auth request resolved
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(3)) {
            self.loginButton.loading = false
        }
        // TODO : Send server request for authentification
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        // enables tapping outside fields to dismiss keyboard
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(
            target: self,
            action: #selector(self.hideKeyboard)
        )
        self.view.addGestureRecognizer(tap)

        username!.placeholder = NSLocalizedString("username", comment: "Access code")
        password!.placeholder = NSLocalizedString("password", comment: "Password")
        
        let usernameRightSideButton = UIButton(type: .custom)
        usernameRightSideButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: -16, bottom: 0, right: 0)
        usernameRightSideButton.frame = CGRect(x: CGFloat(username!.frame.size.width - 25), y: CGFloat(5), width: CGFloat(25), height: CGFloat(25))
        let disclosure = UITableViewCell()
        disclosure.frame = usernameRightSideButton.bounds
        disclosure.accessoryType = .detailButton
        disclosure.isUserInteractionEnabled = false
        usernameRightSideButton.addSubview(disclosure)
        usernameRightSideButton.addTarget(self, action: #selector(usernameInfo), for: .touchUpInside)
        username!.rightView = usernameRightSideButton
        username!.rightViewMode = .always
        
        passwordRightSideButton.setImage(UIImage(named: "eyeClosed"), for: .normal)
        passwordRightSideButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: -16, bottom: 0, right: 0)
        passwordRightSideButton.frame = CGRect(x: CGFloat(username!.frame.size.width - 25), y: CGFloat(5), width: CGFloat(25), height: CGFloat(25))
        
        passwordRightSideButton.addTarget(self, action: #selector(passwordToggle), for: .touchUpInside)
        password!.rightView = passwordRightSideButton
        password!.rightViewMode = .always
        
        madeBy!.text = NSLocalizedString("madeBy", comment: "Réalisé par")
        loginButton!.setTitle(NSLocalizedString("login", comment: "Login"), for: UIControl.State.normal)
    }

    @objc private func hideKeyboard() {
        self.view.endEditing(true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @objc func usernameInfo(sender: UIButton!) {
        let alert = UIAlertController(title: NSLocalizedString("universalAccessCode", comment: "universalAccessCode"), message: NSLocalizedString("universalAccessCodeInfo", comment: "universalAccessCodeInfo"), preferredStyle: .actionSheet)
        
        alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
        
        self.present(alert, animated: true)
    }
    
    @objc func passwordToggle(sender: UIButton!) {
        password!.togglePasswordVisibility()
        passwordHidden = !passwordHidden
        if passwordHidden {
            passwordRightSideButton.setImage(UIImage(named: "eyeClosed"), for: .normal)
        } else {
            passwordRightSideButton.setImage(UIImage(named: "eyeOpen"), for: .normal)
        }
        
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

extension UIViewController {
    class func displaySpinner(onView : UIView) -> UIView {
        let spinnerView = UIView.init(frame: onView.bounds)
        spinnerView.backgroundColor = UIColor.init(red: 0, green: 0, blue: 0, alpha: 0.5)
        let activityIndicator = UIActivityIndicatorView.init(style: .whiteLarge)
        activityIndicator.startAnimating()
        activityIndicator.center = spinnerView.center
        
        DispatchQueue.main.async {
            spinnerView.addSubview(activityIndicator)
            onView.addSubview(spinnerView)
        }
        
        return spinnerView
    }
    
    class func removeSpinner(spinner :UIView) {
        DispatchQueue.main.async {
            spinner.removeFromSuperview()
        }
    }
}
