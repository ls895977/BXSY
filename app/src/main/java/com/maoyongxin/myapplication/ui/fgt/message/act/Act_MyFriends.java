package com.maoyongxin.myapplication.ui.fgt.message.act;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.FriendsInfo;
import com.maoyongxin.myapplication.http.request.ReqGetMyFriendsList;
import com.maoyongxin.myapplication.http.response.FriendsResponse;
import com.maoyongxin.myapplication.server.PinyinComparator;
import com.maoyongxin.myapplication.tool.CharacterParser;
import com.maoyongxin.myapplication.ui.fgt.message.act.myfriends.adapter.SortGroupMemberAdapter;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.rong.imkit.mention.SideBar;

/**
 * 通讯录
 */
public class Act_MyFriends extends BaseAct {
    private LinearLayout line_header;
    private SelectableRoundedImageView mSelectableRoundedImageView;
    private TextView mNameTextView;
    private TextView mNoFriends;
    private TextView mUnreadTextView;
    private View mHeadView;
    private EditText mSearchEditText;
    private ListView mListView;
    private PinyinComparator mPinyinComparator;
    private SideBar mSidBar;
    /**
     * 中部展示的字母提示
     */
    private TextView mDialogTextView;

    private List<FriendsInfo.FriendList> mFilteredFriendList;
    /**
     * 好友列表的 mFriendListAdapter
     */
    private SortGroupMemberAdapter adapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser mCharacterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private String mId;
    private String mCacheName;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {
    }

    @Override
    public int initLayoutId() {
        return R.layout.act_my_friends;
    }

    @Override
    public void initView() {
        hideHeader();
        getViewAndClick(R.id.friends_back);
        getViewAndClick(R.id.fridends_add);
        mSearchEditText = getView(R.id.search);
        mListView = getView(R.id.friends_mylist);
        mNoFriends = getView(R.id.title_layout_no_friends);
        mSidBar = getView(R.id.sidrbar);
        mDialogTextView = getView(R.id.title_layout_catalog);
        mSidBar.setTextView(mDialogTextView);
        LayoutInflater mLayoutInflater = LayoutInflater.from(getActivity());
        mHeadView = mLayoutInflater.inflate(R.layout.item_contact_list_header, null);
        getViewAndClick(mHeadView, R.id.re_group);
        getViewAndClick(mHeadView, R.id.re_record);
        mListView.addHeaderView(mHeadView);
        updatePersonalUI();
        //设置右侧触摸监听
        mSidBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }

            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initData() {
        mFilteredFriendList = new ArrayList<>();
        mListView.setAdapter(adapter);
        //实例化汉字转拼音类
        mCharacterParser = CharacterParser.getInstance();
        mPinyinComparator = new PinyinComparator();
        getFriendList();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back:
                finish();
                break;
            case R.id.fridends_add://添加好友
                startAct(Act_FindAndSearch.class);
                break;
            case R.id.re_group://群组
                startAct(Act_GroupList.class);
                break;
            case R.id.re_record://申请记录
                startAct(Act_ApplicationRecord.class);
                break;
        }
    }

    private void updatePersonalUI() {
        mId = MyApplication.getCurrentUserInfo().getUserId();
        mCacheName = MyApplication.getCurrentUserInfo().getUserName();
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<FriendsInfo.FriendList> filterDateList = new ArrayList<FriendsInfo.FriendList>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mFilteredFriendList;
            mNoFriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (FriendsInfo.FriendList sortModel : mFilteredFriendList) {
                String name = sortModel.getUserName();
                if (name.indexOf(filterStr.toString()) != -1
                        || mCharacterParser.getSpelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, mPinyinComparator);
        adapter.updateListView(filterDateList);
        if (filterDateList.size() == 0) {
            mNoFriends.setVisibility(View.GONE);
        }
    }

    private void getFriendList() {
        ReqGetMyFriendsList.getFriends(getActivity(), getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<FriendsResponse>() {
            @Override
            public Class<FriendsResponse> getEntityClass() {
                return FriendsResponse.class;
            }

            @Override
            public void onSuccess(FriendsResponse resp) {
                if (resp.is200()) {
                    mFilteredFriendList = resp.obj.getFriendList();
                    String[] names = new String[mFilteredFriendList.size()];
                    for (int i = 0; i < mFilteredFriendList.size(); i++) {
                        names[i] = mFilteredFriendList.get(i).getUserName();
                    }
                    mFilteredFriendList = filledData(mFilteredFriendList);
                    // 根据a-z进行排序源数据
                    Collections.sort(mFilteredFriendList, mPinyinComparator);
                    adapter = new SortGroupMemberAdapter(getActivity(), mFilteredFriendList);
                    mListView.setAdapter(adapter);
                    if (mFilteredFriendList.size() == 0) {
                        mNoFriends.setVisibility(View.VISIBLE);
                    } else {
                        mNoFriends.setVisibility(View.GONE);
                    }
                } else {
                    MyToast.show(getActivity(), resp.msg);
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<FriendsInfo.FriendList> filledData(List<FriendsInfo.FriendList> date) {
        List<FriendsInfo.FriendList> mSortList = new ArrayList<FriendsInfo.FriendList>();
        for (int i = 0; i < date.size(); i++) {
            FriendsInfo.FriendList sortModel = new FriendsInfo.FriendList();
            sortModel.setUserName(date.get(i).getUserName());
            sortModel.setUserId(date.get(i).getUserId());
            sortModel.setUserPhone(date.get(i).getUserPhone());
            sortModel.setHeadImg(date.get(i).getHeadImg());
            sortModel.setUserNoteName(date.get(i).getUserNoteName());
            // 汉字转换成拼音
            String pinyin = mCharacterParser.getSpelling(date.get(i).getUserName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
