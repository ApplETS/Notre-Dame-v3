//
//  LoginViewController.swift
//  ETS-mobile
//
//  Created by Emmanuel Proulx on 2018-07-29.
//  Copyright © 2018 applETS. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {

    
    @IBAction func sendLoginInfo(_ sender: Any) {
        print(username.text!,password.text!)
    }
    
    
    @IBOutlet weak var username: UITextField!
    
    @IBOutlet weak var password: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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
