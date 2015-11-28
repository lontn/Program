package com.fcu.gtml.svm;

import java.io.IOException;
import java.sql.*;
import java.util.Vector;

import libsvm.*;

public class libSVMdemo {
    svm_parameter _param;
    svm_problem _prob;
    String _model_file;

    protected void loadData(boolean is_training) {
        String limit;
        if (is_training) { // training
            System.out.print("Loading training data...");
            limit = " WHERE id <= 4700";
        } else {  //測試階段
            System.out.print("Loading testing data...");
            limit = " WHERE id > 4700";
        }

        int max_index = 0; //紀錄資料中最大的維度(用來產生gamma參數)
        _prob = new svm_problem();
        Vector<Double> vy = new Vector<Double>();
        Vector<svm_node[]> vx = new Vector<svm_node[]>();

        try {
            Class.forName("org.sqlite.JDBC"); //連接資料庫
            Connection conn = DriverManager.getConnection("jdbc:sqlite:sparseData.s3db");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM data" + limit);

            while (rs.next()) {
                vy.addElement(rs.getDouble("label"));//labal為資料的標籤，為-1或+1
                                                     //SVM通常就是解決是好(+1)或壞(-1)的問題

                int rdk1 = rs.getInt("rdk1"), rdk2 = rs.getInt("rdk2");
                //讀取兩個維度的資料，這裡只有「有這個維度(true)」與「沒有這個維度(false)」
                //因此兩個欄位只填寫維度的index，例如填寫30，就代表第30維有資料
                //假設rdk1=30，rdk2=2789，則這個node只有這兩個維的值是true，
                //其他2998維(比如第31, 32...維)的值都是false
                if (rdk1 == rdk2) { //兩個維度的index相等，只放一個
                    svm_node[] x = new svm_node[1];
                    x[0] = new svm_node();
                    x[0].index = rdk1;
                    x[0].value = 1;
                    max_index = Math.max(max_index, rdk1);
                    vx.addElement(x);
                } else {
                    if (rdk2 < rdk1) { // 如果第二個index比第一個小，交換
                        rdk1 = rdk2;
                        rdk2 = rs.getInt("rdk1");
                    }

                    svm_node[] x = new svm_node[2]; //建立SVM node的陣列
                    x[0] = new svm_node();
                    x[0].index = rdk1;              //維度的index例如30
                    x[0].value = 1;                 //有值，為true
                    x[1] = new svm_node();
                    x[1].index = rdk2;              //維度的index例如2789
                    x[1].value = 1;
                    max_index = Math.max(max_index, rdk2);  //記下目前用到最大的維度
                    vx.addElement(x);                   //儲存SVM node的陣列
                }
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (max_index > 0)
            _param.gamma = 1.0 / max_index; // 1/num_features

        _prob.l = vy.size(); //svm node的數量
        _prob.x = new svm_node[_prob.l][];
        for (int i = 0; i < _prob.l; i++) {
            _prob.x[i] = vx.elementAt(i); //儲存每個node的向量
            _prob.y = new double[_prob.l];
        }
        for (int i = 0; i < _prob.l; i++) {
            _prob.y[i] = vy.elementAt(i); //儲存每個node的label
        }

        System.out.println("Done!!");
    }

    protected void training() {
        loadData(true);//這邊呼叫loadData()，使用true參數是因為在training階段
                       //透過loadData，將資料庫的資料儲存在全域變數_prob裡面

        System.out.print("Training...");
        _model_file = "svm_model.txt"; //指定SVM model儲存的檔案名稱

        try {
            svm_model model = svm.svm_train(_prob, _param);  //訓練SVM model
            System.out.println("Done!!");
            svm.svm_save_model(_model_file, model);  //將訓練結果寫入檔案
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void testing() {
        loadData(false); //讀取剩下的300分資料，轉換成SVM問題(存在_prob裡)

        svm_model model;
        int correct = 0, total = 0;
        try {
            model = svm.svm_load_model(_model_file); //載入model

            for (int i = 0; i < _prob.l; i++) { //對problem 裡的每個SVM node
                double v;
                svm_node[] x = _prob.x[i]; //取出svm node
                v = svm.svm_predict(model, x); //把node餵給預測器
                                               //這時預測器會依照model與node內的向量資訊，產生預測的數值(-1或1)
                total++;
                if (v == _prob.y[i]) {
                    correct++;
                }
            }

            double accuracy = (double) correct / total * 100;
            System.out.println("Accuracy = " + accuracy + "% (" + correct + "/" + total + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    libSVMdemo() {
        // default values
        _param = new svm_parameter();

        _param.svm_type = svm_parameter.C_SVC;
        _param.kernel_type = svm_parameter.LINEAR;
        _param.degree = 3;
        _param.gamma = 0; // 1/num_features
        _param.coef0 = 0;
        _param.nu = 0.5;
        _param.cache_size = 100;
        _param.C = 1;
        _param.eps = 1e-3;
        _param.p = 0.1;
        _param.shrinking = 1;
        _param.probability = 0;
        _param.nr_weight = 0;
        _param.weight_label = new int[0];
        _param.weight = new double[0];

        training();
        testing();
    }

    public static void main(String[] args) {
        libSVMdemo ld = new libSVMdemo();
    }
}
