package com.example.ventz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventz.model.Dados;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Now use the inflated view to find your TextView
        TextView labelNome = view.findViewById(R.id.labelNome);
        TextView labelEmail = view.findViewById(R.id.labelEmail);

        ImageButton myImageButton = view.findViewById(R.id.imageButton);
        // add event to myImageButton
        myImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Abrindo leitor de QR Code...", Toast.LENGTH_SHORT).show();
            }
        });

        // You can now set text or other properties on txtNome


// Get the URL and user ID from Dados
//        String url = Dados.getInstance().getUrl() + "/usuarios/buscarPorId/" + Dados.getInstance().getIdUsuarioLogado();
//        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
//
//        // Use StringRequest to get the response as a plain string
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    // Example response: "Usuario{idUsuario=8, nome='a', email='a', senha='a', deckUsuarios=[...]}"
//                    try {
//                        // Extract 'nome' and 'email' values manually
//                        String nome = response.split("nome='")[1].split("'")[0];
//                        String email = response.split("email='")[1].split("'")[0];
//
//                        // Set the extracted values to TextViews
//                        labelNome.setText(nome);
//                        labelEmail.setText(email);
//
//                    } catch (Exception e) {
//                        Toast.makeText(requireContext(), "Error parsing data", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                error -> Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());
//
//        Volley.newRequestQueue(requireContext()).add(stringRequest);






//        return inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }
}