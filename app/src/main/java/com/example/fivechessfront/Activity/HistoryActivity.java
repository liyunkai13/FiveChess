package com.example.fivechessfront.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fivechessfront.Entity.Account;
import com.example.fivechessfront.Entity.GameHistory;
import com.example.fivechessfront.R;
import com.example.fivechessfront.utils.AccountManager;
import com.example.fivechessfront.utils.MyHelper;

import java.util.List;
import java.util.Objects;

public class HistoryActivity extends Activity {
    private MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //操作数据库
        myHelper = new MyHelper(this);

        // 查询历史记录数据
        Account account = AccountManager.getInstance().getAccount();
        List<GameHistory> gameHistoryList = selectGameHistory(account);

        // 创建适配器
        HistoryAdapter adapter = new HistoryAdapter(this, gameHistoryList);

        // 获取ListView
        ListView listView = findViewById(R.id.listView);

        // 设置适配器到ListView
        listView.setAdapter(adapter);
    }

    private List<GameHistory> selectGameHistory(Account account) {
        // 实现你的查询逻辑，返回历史记录数据列表
        return myHelper.selectGameHistory(account);
    }
}
class HistoryAdapter extends BaseAdapter {
    private final List<GameHistory> gameHistoryList;
    private final LayoutInflater inflater;

    public HistoryAdapter(Context context, List<GameHistory> gameHistoryList) {
        this.gameHistoryList = gameHistoryList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return gameHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return gameHistoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // 可以返回某个唯一标识，例如历史记录的ID字段
        // 如果历史记录类中有一个唯一标识字段，可以直接返回它
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 创建或复用列表项视图
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_game_history, parent, false);
        }

        // 获取当前位置的历史记录对象
        GameHistory gameHistory = (GameHistory) getItem(position);

        // 在列表项视图中设置数据，例如设置用户名、比赛结果等
        TextView resultTextView = convertView.findViewById(R.id.resultTextView);
        TextView colorTextView = convertView.findViewById(R.id.colorTextView);
        TextView cntTextView = convertView.findViewById(R.id.cntTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);

        // 设置历史记录对象的数据到对应的视图
        resultTextView.setText(gameHistory.getResult());
        if (Objects.equals(gameHistory.getResult(), "胜利"))
            resultTextView.setTextColor(0xFF00FF00);
        else
            resultTextView.setTextColor(0xFFFF0000);
        colorTextView.setText(gameHistory.getColor());
        cntTextView.setText(String.valueOf(gameHistory.getCnt()));
        dateTextView.setText(gameHistory.getDATE_FORMAT());

        return convertView;
    }
}