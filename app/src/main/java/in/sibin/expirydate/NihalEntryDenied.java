package in.sibin.expirydate;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


public class NihalEntryDenied extends DialogFragment {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_ROLLNUMBER =  "roll";
    public static final String EXTRA_DEPT = "dept";
    public static final String EXTRA_SUB = "subdept";

    TextView tvRollNumber, tvName, tvDept, tvSub;

    public NihalEntryDenied() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_nihal_deinied, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);



        return view;
    }

}
