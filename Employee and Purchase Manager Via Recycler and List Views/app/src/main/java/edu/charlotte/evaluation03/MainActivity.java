package edu.charlotte.evaluation03;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.charlotte.evaluation03.fragments.EmployeesFragment;
import edu.charlotte.evaluation03.fragments.PurchaseDetailsFragment;
import edu.charlotte.evaluation03.fragments.PurchasesFragment;
import edu.charlotte.evaluation03.models.Employee;
import edu.charlotte.evaluation03.models.Purchase;

public class MainActivity extends AppCompatActivity implements EmployeesFragment.EmployeesListener, PurchasesFragment.PurchasesListener, PurchaseDetailsFragment.PurchaseDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new EmployeesFragment())
                .commit();
    }

    @Override
    public void onEmployeeSelected(Employee employee) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, PurchasesFragment.newInstance(employee))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPurchaseSelected(Purchase purchase) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, PurchaseDetailsFragment.newInstance(purchase))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelSelected() {
        getSupportFragmentManager().popBackStack();
    }
}