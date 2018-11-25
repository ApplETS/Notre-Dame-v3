//
//  DrawerTableViewController.swift
//  ETS-mobile
//
//  Created by Emmanuel on 2018-04-21.
//  Copyright © 2018 applETS. All rights reserved.
//

import UIKit

class EmergencyTableViewController: UITableViewController {

    
    @IBOutlet weak var titleLabel: UINavigationItem!
    @IBOutlet weak var emergencyCallLabel: UILabel!
    
    @IBOutlet weak var internCallLabel: UILabel!
    
    @IBOutlet weak var extensionLabel: UILabel!
    
    @IBOutlet weak var bombThreatLabel: UILabel!
    
    @IBOutlet weak var suspiciousPackageLabel: UILabel!
    
    @IBOutlet weak var fireLabel: UILabel!
    
    @IBOutlet weak var suspiciousSmellLabel: UILabel!
    
    @IBOutlet weak var elevatorMalfunctionLabel: UILabel!
    
    @IBOutlet weak var powerOutageLabel: UILabel!
    
    @IBOutlet weak var armedPersonLabel: UILabel!
    
    @IBOutlet weak var medicalEmergencyLabel: UILabel!
    
    @IBAction func emergencyCall(_ sender: Any) {
        if let phoneCallUrl = URL(string : "tel://514-396-8900") {
            let application:UIApplication = UIApplication.shared
            if (application.canOpenURL(phoneCallUrl)) {
                application.open(phoneCallUrl, options: [:], completionHandler: nil)
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()


        emergencyCallLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("emergencyCall", comment: "Emergency call"))

        titleLabel.title = NSLocalizedString("emergencies", comment: "Title for emergencies")
        
        
        
        internCallLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("internCall", comment: "From inside campus"))
        
        extensionLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("extension", comment: "Ext. 55"))
        
        bombThreatLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("bombThreat", comment: "Bomb threat"))
        
        suspiciousPackageLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("suspiciousPackage", comment: "Suspicious package"))
        
        fireLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("fire", comment: "Fire"))
        
        suspiciousSmellLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("suspiciousSmell", comment: "Suspicious smell"))
        
        elevatorMalfunctionLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("elevatorMalfunction", comment: "Elevator malfunction"))
        
        powerOutageLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("powerOutage", comment: "Power outage"))
        
        armedPersonLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("armedPerson", comment: "Armed person"))
        
        medicalEmergencyLabel!.attributedText = NSMutableAttributedString(string: NSLocalizedString("medicalEmergency", comment: "Medical emergency"))
        
        self.tableView?.rowHeight = 40.0
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    // Associate the language mapped labels to the section headers of the table view
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        switch (section) {
            case 0 :
                return NSLocalizedString("reachSecurity", comment: "Reach security")
            
            case 1 :
                return NSLocalizedString("emergencyProcedures", comment: "Emergency procedures")
            default :
                return ""
        }
    }
    
    /*override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 0
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return 0
    }
     */
    /*
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
