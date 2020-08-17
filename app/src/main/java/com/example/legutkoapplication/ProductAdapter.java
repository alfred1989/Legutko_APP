package com.example.legutkoapplication;

import android.content.Context;
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
        TextView commentaryInPL = view.findViewById(R.id.plantation_area);
        TextView batch = view.findViewById(R.id.batch);
        TextView comment = view.findViewById(R.id.comment);

        name.setText(product.getName());
        producer.setText(product.getProducer());
        code.setText(String.valueOf(product.getCode()));
        species.setText(product.getSpecies());
        standardPlantation.setText(product.getStandardPlantation());
        commentaryInPL.setText(product.getPlantation_area());
        batch.setText(product.getBatch());
        comment.setText(product.getComment());

        return view;
    }
}