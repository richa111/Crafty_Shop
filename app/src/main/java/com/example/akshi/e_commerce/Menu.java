package com.example.akshi.e_commerce;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by akshi on 02-08-2017.
 */

public class Menu extends Fragment {

    TextView txtProductEntry, txtEditProduct,txtDeleteProduct;
    ImageView imgView,imgView2,imgView3,imgView4;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu, null, false);

        txtProductEntry = (TextView) view.findViewById(R.id.txtProductEntry);
        txtEditProduct = (TextView) view.findViewById(R.id.txtEditProduct);
        txtDeleteProduct=(TextView)view.findViewById(R.id.txtDeleteProduct);
        imgView=(ImageView)view.findViewById(R.id.imgView);
        // imgView2=(ImageView)view.findViewById(R.id.imgView2);
        imgView3=(ImageView)view.findViewById(R.id.imgView3);
        imgView4=(ImageView)view.findViewById(R.id.imgView4);

        imgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),ProductEntry.class);
                getActivity().startActivity(intent);

            }
        });
        txtProductEntry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),ProductEntry.class);
                getActivity().startActivity(intent);
            }
        });

        imgView3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),EditProduct.class);
                getActivity().startActivity(intent);

            }
        });
        txtEditProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),EditProduct.class);
                getActivity().startActivity(intent);
            }
        });


        imgView4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),DeleteProduct.class);
                getActivity().startActivity(intent);

            }
        });
        txtDeleteProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),DeleteProduct.class);
                getActivity().startActivity(intent);
            }
        });


        /* txtProductEntry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeFragement(v);

            }

        });


        txtEditProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeFragement(v);

            }

        });

        return view;
    }

    public void changeFragement(View v)
    {
        Fragment fr=null;
        if(v== v.findViewById(R.id.txtProductEntry))
        {
            fr=new ProductEntry();
        }
        else
        {
            fr=new EditProduct();
        }
        FragmentManager fm = getFragmentManager(); //getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragementContainer, fr);
        fragmentTransaction.commit();
    }*/
        return view;
    }

}

