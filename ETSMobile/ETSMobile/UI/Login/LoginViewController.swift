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
    @IBOutlet weak var madeByLogo: UIImageView!
    @IBOutlet weak var forgotPasswordLink: UIButton!

    let passwordRightSideButton = UIButton(type: .custom)
    var passwordHidden = true
    var isSecureTextEntry = true

    // whether we are waiting on a server response, disables fields and login button
    private var _loading = false
    var loading: Bool {
        get { return self._loading }
        set {
            if self._loading != newValue {
                self._loading = newValue
                self.username.isEnabled = !self._loading
                self.password.isEnabled = !self._loading
                self.loginButton.loading = self._loading
            }
        }
    }

    // whether the fields are valid, controls if we can push the login button
    private var _valid = true
    var valid: Bool {
        get { return self._valid }
        set {
            if self._valid != newValue {
                self._valid = newValue
                self.loginButton.isEnabled = self._valid
            }
        }
    }

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    @IBAction func sendLoginInfo(_ sender: Any) {
        self.loading = true
        // just for testing, we would do that after the auth request resolved
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(3)) {
            self.loading = false
            self.performSegue(withIdentifier: "goToHome", sender: self)
        }
        // TODO : Send server request for authentification
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // enables tapping outside fields to dismiss keyboard
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(
            target: self,
            action: #selector(self.hideKeyboard)
        )
        self.view.addGestureRecognizer(tap)

        // logo click redirect to club homepage
        let logoTap: UITapGestureRecognizer = UITapGestureRecognizer(
            target: self,
            action: #selector(self.onLogoClick)
        )
        self.madeByLogo.addGestureRecognizer(logoTap)

        username!.placeholder = NSLocalizedString("username", comment: "Access code")
        password!.placeholder = NSLocalizedString("password", comment: "Password")
        username.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)
        password.addTarget(self, action: #selector(textFieldDidChange(_:)), for: .editingChanged)

        let usernameRightSideButton = UIButton(type: .custom)
        usernameRightSideButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: -16, bottom: 0, right: 0)
        usernameRightSideButton.frame = CGRect(
            x: CGFloat(username!.frame.size.width - 25),
            y: CGFloat(5),
            width: CGFloat(25),
            height: CGFloat(25)
        )
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
        passwordRightSideButton.frame = CGRect(
            x: CGFloat(username!.frame.size.width - 25),
            y: CGFloat(5),
            width: CGFloat(25),
            height: CGFloat(25)
        )
        passwordRightSideButton.addTarget(self, action: #selector(passwordToggle), for: .touchUpInside)

        password!.rightView = passwordRightSideButton
        password!.rightViewMode = .always

        madeBy!.text = NSLocalizedString("madeBy", comment: "Réalisé par")
        loginButton!.setTitle(NSLocalizedString("login", comment: "Login"), for: UIControl.State.normal)

        forgotPasswordLink!.setTitle(NSLocalizedString("forgotPassword", comment: "Forgot password"), for: .normal)

        self.valid = false
        NotificationCenter.default.addObserver(self, selector: #selector(appMovedToForeground), name: UIApplication.willEnterForegroundNotification, object: nil)
    }

    @IBAction func onPasswordForgot(_ sender: UIButton) {
        UIApplication.shared.open(Environment.current.passwordReset())
    }

    @objc func onLogoClick() {
        UIApplication.shared.open(Environment.current.clubHomepage())
    }

    @objc func textFieldDidChange(_ textField: UITextField) {
        self.valid = LoginValidation.validate(
            code: username!.text ?? "",
            password: password!.text ?? ""
        )
    }

    @objc private func hideKeyboard() {
        self.view.endEditing(true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @objc func usernameInfo(sender: UIButton!) {
        let alert = UIAlertController(
            title: NSLocalizedString(
                "universalAccessCode",
                comment: "universalAccessCode"
            ),
            message: NSLocalizedString(
                "universalAccessCodeInfo",
                comment: "universalAccessCodeInfo"
            ),
            preferredStyle: .actionSheet
        )

        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))

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
    
    @objc func appMovedToForeground() {
        print("app moved to foreground")
        loginButton.style()
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
