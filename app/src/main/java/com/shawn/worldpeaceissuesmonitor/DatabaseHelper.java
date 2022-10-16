//
// DatabaseHelper.java
//
// This object encapsulates all the Firebase functionality of our application
//
// This file is part of the course "Build a Firebase Android Application"
//
// Written by Harrison Kong @ coursera.org
//

package com.shawn.worldpeaceissuesmonitor;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;


public final class DatabaseHelper {

    public static final void buildIssuesList(@NonNull DataSnapshot snapshot, ArrayList<Issue> issuesList) {

        issuesList.clear();  // clear the old data

        for (DataSnapshot issue : snapshot.getChildren()) {

            // retrieve the key
            String key = issue.getKey();
            // retrieve the severity
            String severity =issue.child("severity").getValue(String.class);

            // retrieve the resolved status
            String resolved = issue.child("resolved").getValue(String.class);
            // retrieve the description
            String description = issue.child("description").getValue(String.class);

            // make a new Issue object and add it to the ArrayList
            issuesList.add(new Issue(key, severity, resolved, description));
        }

    }

    public static final void addNewIssue(Issue newIssue) {

        // use the reference to root node /issues
        // Task 5: build the reference and call it rootRef, the path is /issues"
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("/issues");
        // to push a new child node and get the reference to it
        DatabaseReference newIssueRef = rootRef.push();
        // set the children nodes of this new reference to our object's properties
        newIssueRef.setValue(newIssue);
    }

    public static final void deleteIssue(String keyToDelete) {

        // get a reference to the issue child node to be deleted
        // Task 6: build the reference and call it issueRef, the path is /issues/<keyToDelete>"
        DatabaseReference issueRef = FirebaseDatabase.getInstance().getReference("/issues/" + keyToDelete);
        // remove the child node and its children
        issueRef.removeValue();
    }

    public static final void updateIssue(String issueKey, String newStatus) {

        // get a reference to the child node to be updated and to the "resolved" child node
        // Task 7: build the reference and call it issueRef, the path is /issues/<issueKey>/resolved"
        DatabaseReference issueRef = FirebaseDatabase.getInstance().getReference("/issues/" + issueKey + "/resolved");
        // set the value of the node
        issueRef.setValue(newStatus);
    }
}
