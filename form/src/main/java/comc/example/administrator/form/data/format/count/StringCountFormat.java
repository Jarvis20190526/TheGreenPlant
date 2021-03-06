package comc.example.administrator.form.data.format.count;


import java.util.HashSet;
import java.util.Set;

import comc.example.administrator.form.data.column.Column;

/**
 * Created by huang on 2017/11/6.
 */

public class StringCountFormat<T> implements ICountFormat<T,Integer> {

    private Set<String> valueSet;
    private int count;
    private Column<T> column;

    public StringCountFormat(Column<T> column){
        this.column = column;
        this.valueSet = new HashSet<>();
    }


    @Override
    public void count(T t) {
        String value;
        if(column.getFormat() != null) {
            value = column.getFormat().format(t);
        }else{
            value = t == null ? "" : t.toString();
        }
        if(value != null && !valueSet.contains(value)
                && !"".equals(value)){
            count++;
            valueSet.add(value);
        }
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public String getCountString() {
        return String.valueOf(count);
    }

    @Override
    public void clearCount() {
        valueSet.clear();
        count = 0;
    }
}
