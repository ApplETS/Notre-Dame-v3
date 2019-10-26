//
//  LoginViewController.swift
//  ETS-mobile
//
//  Created by Emmanuel Proulx on 2018-07-29.
//  Copyright © 2018 applETS. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: ETSTextField!
    @IBOutlet weak var loginButton: ETSButton!
    @IBOutlet weak var madeByLabel: UILabel!
    @IBOutlet weak var clubButton: UIButton!
    @IBOutlet weak var forgotPasswordButton: UIButton!

    private(set) var isLoading: Bool = false {
        didSet {
            if isLoading != oldValue {
                usernameTextField.isEnabled = !isLoading
                passwordTextField.isEnabled = !isLoading
                loginButton.isLoading = isLoading
            }
        }
    }

    private(set) var isValid: Bool = false {
        didSet {
            if isValid != oldValue {
                loginButton.isEnabled = isValid
            }
        }
    }

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(backgroundTapped))
        view.addGestureRecognizer(tapGestureRecognizer)

        usernameTextField.placeholder = NSLocalizedString("username", comment: "Access code")
        passwordTextField.placeholder = NSLocalizedString("password", comment: "Password")
        usernameTextField.addTarget(self, action: #selector(textDidChange(_:)), for: .editingChanged)
        passwordTextField.addTarget(self, action: #selector(textDidChange(_:)), for: .editingChanged)

        let usernameInfoButton = UIButton(type: .infoLight)
        usernameInfoButton.bounds.size = CGSize(width: 40, height: 40)
        usernameInfoButton.addTarget(self, action: #selector(usernameInfo), for: .touchUpInside)
        usernameInfoButton.translatesAutoresizingMaskIntoConstraints = false
        let usernameInfoButtonContainer = UIView()
        usernameInfoButtonContainer.translatesAutoresizingMaskIntoConstraints = false
        usernameInfoButtonContainer.widthAnchor.constraint(equalToConstant: 36.0).isActive = true
        usernameInfoButtonContainer.heightAnchor.constraint(equalToConstant: 40.0).isActive = true
        usernameInfoButtonContainer.addSubview(usernameInfoButton)
        usernameInfoButton.topAnchor.constraint(equalTo: usernameInfoButtonContainer.topAnchor).isActive = true
        usernameInfoButton.leftAnchor.constraint(equalTo: usernameInfoButtonContainer.leftAnchor).isActive = true
        usernameInfoButton.bottomAnchor.constraint(equalTo: usernameInfoButtonContainer.bottomAnchor).isActive = true
        usernameTextField.rightView = usernameInfoButtonContainer
        usernameTextField.rightViewMode = .always

        let showPasswordButton = UIButton()
        showPasswordButton.setImage(UIImage(named: "eyeClosed"), for: .normal)
        showPasswordButton.setImage(UIImage(named: "eyeOpen"), for: .selected)
        showPasswordButton.bounds.size = CGSize(width: 40, height: 40)
        showPasswordButton.addTarget(self, action: #selector(togglePasswordVisibility), for: .touchUpInside)
        showPasswordButton.translatesAutoresizingMaskIntoConstraints = false
        let showPasswordButtonContainer = UIView()
        showPasswordButtonContainer.translatesAutoresizingMaskIntoConstraints = false
        showPasswordButtonContainer.widthAnchor.constraint(equalToConstant: 32.0).isActive = true
        showPasswordButtonContainer.heightAnchor.constraint(equalToConstant: 40.0).isActive = true
        showPasswordButtonContainer.addSubview(showPasswordButton)
        showPasswordButton.topAnchor.constraint(equalTo: showPasswordButtonContainer.topAnchor).isActive = true
        showPasswordButton.leftAnchor.constraint(equalTo: showPasswordButtonContainer.leftAnchor).isActive = true
        showPasswordButton.bottomAnchor.constraint(equalTo: showPasswordButtonContainer.bottomAnchor).isActive = true
        passwordTextField.rightView = showPasswordButtonContainer
        passwordTextField.rightViewMode = .always

        madeByLabel.text = NSLocalizedString("madeBy", comment: "Réalisé par")
        loginButton.setTitle(NSLocalizedString("login", comment: "Login"), for: UIControl.State.normal)

        forgotPasswordButton.setTitle(NSLocalizedString("forgotPassword", comment: "Forgot password"), for: .normal)
        self.view.layoutIfNeeded()
    }

    @IBAction func loginButtonTapped(_ sender: Any) {
        isLoading = true
        // just for testing, we would do that after the auth request resolved
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(3)) { [weak self] in
            self?.isLoading = false
            self?.performSegue(withIdentifier: "goToHome", sender: self)
        }
        // TODO : Send server request for authentification
    }

    @IBAction func forgotPasswordButtonTapped(_ sender: UIButton) {
        UIApplication.shared.open(Environment.current.passwordReset())
    }

    @IBAction func clubButtonTapped() {
        UIApplication.shared.open(Environment.current.clubHomepage())
    }

    @objc func textDidChange(_ textField: UITextField) {
        isValid = LoginValidation.validate(
            code: usernameTextField.text ?? "",
            password: passwordTextField.text ?? ""
        )
    }

    @objc private func backgroundTapped() {
        view.endEditing(true)
    }

    @objc func usernameInfo(sender: UIButton) {
        let alertController = UIAlertController(
            title: NSLocalizedString("universalAccessCode", comment: "universalAccessCode"),
            message: NSLocalizedString("universalAccessCodeInfo", comment: "universalAccessCodeInfo"),
            preferredStyle: .alert
        )
        alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        present(alertController, animated: true)
    }

    @objc func togglePasswordVisibility(sender: UIButton) {
        sender.isSelected = !sender.isSelected
        passwordTextField.togglePasswordVisibility()
    }
}
