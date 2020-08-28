package com.example.legutkoapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.legutkoapplication.model.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    Context mCtx;
    int listLayoutRes;
    List<Product> productList;

    public ProductAdapter(Context mCtx, int listLayoutRes, List<Product> productList) {
        super(mCtx, listLayoutRes, productList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        Product product = productList.get(position);

        TextView name = view.findViewById(R.id.name);
        TextView producer = view.findViewById(R.id.producer);
        TextView code = view.findViewById(R.id.code);
        TextView species = view.findViewById(R.id.species);
        TextView standardPlantation = view.findViewById(R.id.standard_plantation);
        TextView batch = view.findViewById(R.id.batch);
        TextView plantation_area = view.findViewById(R.id.plantation_area);
        TextView comment = view.findViewById(R.id.comment);
        TextView contract = view.findViewById(R.id.contract);

        name.setText(product.getName());
        producer.setText(product.getProducer());
        code.setText(String.valueOf(product.getCode()));
        species.setText(product.getSpecies());
        standardPlantation.setText(product.getStandardPlantation());
        batch.setText(product.getBatch());
        comment.setText(product.getComment());
        plantation_area.setText((product.getPlantation_area()));
        contract.setText(product.getContract());
        if (product.getStandardPlantation().equals("Nietypowa")) {
            view.setBackgroundColor(Color.parseColor("#e21e13"));
        }
        if (product.getStandardPlantation().equals("Typowa")) {
            view.setBackgroundColor(Color.parseColor("#ADFF2F"));
        }
        if (product.getStandardPlantation() == null ) {
            view.setBackgroundColor(Color.parseColor("#FFA500"));
        }

        if (product.getStandardPlantation() == null) {
            standardPlantation.setBackgroundColor(Color.parseColor("#FF8C00"));
        }
        if (product.getStandardPlantation().equals("Typowa")) {
            standardPlantation.setBackgroundColor(Color.parseColor("#3da549"));
        }
        if (product.getStandardPlantation().equals("Nietypowa")) {

            standardPlantation.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        return view;
    }
}