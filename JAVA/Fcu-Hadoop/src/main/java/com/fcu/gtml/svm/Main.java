package com.fcu.gtml.svm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.common.utils.StudentEventTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.StudentPlayVideoTrainData;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class Main extends StudentEventTool {
    private static final Logger L = LoggerFactory.getLogger(Main.class);
    static public svm_model sm = new svm_model();;
    static public ArrayList<svm_node[]> sn_all = new ArrayList<svm_node[]>();;
    static public svm_parameter sparam = new svm_parameter();;
    static public svm_problem sprob = new svm_problem();
    static public int feature_num = 2;
	
	public static void main(String[] args) {
        double[] list1 = { 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0 };
        double[] list2 = { 1, 0, 2, 0, 1, 1, 2, 1, 3, 0, 3, 0, 3, 3, 3, 3 };
        double[] list3 = { 2, 0, 2, 0, 1, 1, 2, 0, 2, 1, 2, 0, 2, 1, 1, 2 };
        double[] list4 = { 2, 0, 2, 0, 1, 0, 1, 0, 2, 3, 2, 2, 2, 2, 2, 2 };
        double[] list5 = { 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1 };
        double[] result = { 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1 };

        // 實驗資料
        List<StudentPlayVideoTrainData> predictData = trainDataService.listStudentPlayVideoTrainData();
        //初始化參數
        initialparam();
        sprob.l = 16;
        for (int i = 0; i < 16; i++) {
            svm_node[] sn = new svm_node[5];
            sn[0] = new svm_node();
            sn[1] = new svm_node();
            sn[2] = new svm_node();
            sn[3] = new svm_node();
            sn[4] = new svm_node();
            sn[0].index = 1;
            sn[0].value = list1[i];
            sn[1].index = 2;
            sn[1].value = list2[i];
            sn[2].index = 3;
            sn[2].value = list3[i]; 
            sn[3].index = 4;
            sn[3].value = list4[i];
            sn[4].index = 5;
            sn[4].value = list5[i]; 
            sn_all.add(sn);
        }
        sprob.y = result;
        sprob.x = new svm_node[sprob.l][];
        for (int i = 0; i < sprob.l; i++) {
            sprob.x[i] = sn_all.get(i);
        }
        if (feature_num > 0)
            sparam.gamma = 1.0 / feature_num;
        System.out.println("Training...");
        sm = svm.svm_train(sprob, sparam);
        System.out.println("Saving model...");
        try {
            svm.svm_save_model("record_edx.txt", sm);
        } catch (IOException e) {
            L.error("SVM IOException:{}", e);
        }
        // predict
        System.out.println("predict...");
        svm_node[] demo = new svm_node[5];
        demo[0] = new svm_node();
        demo[1] = new svm_node();
        demo[2] = new svm_node();
        demo[3] = new svm_node();
        demo[4] = new svm_node();
        //predict Data
        for (StudentPlayVideoTrainData data : predictData) {
            demo[0].index = 1;
            demo[0].value = data.getA1();
            demo[1].index = 2;
            demo[1].value = data.getA2();
            demo[2].index = 3;
            demo[2].value = data.getA3();
            demo[3].index = 4;
            demo[3].value = data.getA4();
            demo[4].index = 5;
            demo[4].value = data.getA5();
            svm_node[] x = demo;
            double v = svm.svm_predict(sm, x);
            System.out.println("result:" + v);
            //if(v == sprob.y[i]) correct++;  //如果跟正確答案一樣，則正確數加一
        }
    }

    public static void initialparam() {
        sparam.svm_type = svm_parameter.C_SVC;
        //sparam.kernel_type = svm_parameter.LINEAR;
        sparam.kernel_type = svm_parameter.RBF;
        sparam.degree = 3;
        sparam.gamma = 0;
        sparam.coef0 = 0;
        sparam.nu = 0.5;
        sparam.cache_size = 100;
        sparam.C = 1;
        sparam.eps = 1e-3;
        sparam.p = 0.1;
        sparam.shrinking = 1;
        sparam.probability = 0;
        sparam.nr_weight = 0;
        sparam.weight_label = new int[0];
        sparam.weight = new double[0];
    }
}
