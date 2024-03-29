package b.in.locateafriend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import b.in.locateafriend.Interface.IFirebaseLoadDone;
import b.in.locateafriend.Interface.IRecyclerItemClickListener;
import b.in.locateafriend.Model.User;
import b.in.locateafriend.Utils.Common;
import b.in.locateafriend.ViewHolder.FriendRequestViewHolder;
import b.in.locateafriend.ViewHolder.UserViewHolder;

public class FriendRequestActivity extends AppCompatActivity implements IFirebaseLoadDone {


    FirebaseRecyclerAdapter<User, FriendRequestViewHolder> adapter, searchAdapter;
    RecyclerView recycler_all_user;
    IFirebaseLoadDone firebaseLoadDone;
    MaterialSearchBar searchBar;
    List<String> suggestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);


        //Initialise View
        searchBar = (MaterialSearchBar) findViewById(R.id.material_search_bar);
        searchBar.setCardViewElevation(10);
        searchBar.setAlpha(0);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    if (adapter != null) {
                        recycler_all_user.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        recycler_all_user = (RecyclerView) findViewById(R.id.recycler_all_people);
        recycler_all_user.setClickable(true);
        recycler_all_user.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_all_user.setLayoutManager(layoutManager);
        recycler_all_user.addItemDecoration(new DividerItemDecoration(this, ((LinearLayoutManager) layoutManager).getOrientation()));

        firebaseLoadDone = this;

        loadFriendRequestList();
        loadSearchData();
    }

    private void startSearch(String search_value) {
        Query query = FirebaseDatabase.getInstance().getReference().child(Common.USER_INFORMATION)
                .child(Common.loggedUser.getUid())
                .child(Common.FRIEND_REQUEST)
                .orderByChild("name")
                .startAt(search_value);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        searchAdapter = new FirebaseRecyclerAdapter<User, FriendRequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position, @NonNull User model) {

                holder.txt_user_email.setText(model.getEmail());
                holder.btn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteFriendRequest(model, false);
                        addToAcceptList(model);
                        addUserToFriendContact(model);
                    }
                });
                holder.btn_decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Delete
                        deleteFriendRequest(model, true);
                    }
                });


            }

            @NonNull
            @Override
            public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_friend_request, viewGroup, false);
                return new FriendRequestViewHolder(itemView);
            }
        };

        searchAdapter.startListening();
        recycler_all_user.setAdapter(searchAdapter);
    }

    private void loadFriendRequestList() {
        Query query = FirebaseDatabase.getInstance().getReference().child(Common.USER_INFORMATION)
                .child(Common.loggedUser.getUid())
                .child(Common.FRIEND_REQUEST);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<User, FriendRequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position, @NonNull User model) {

                holder.txt_user_email.setText(model.getEmail());
                holder.btn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteFriendRequest(model, false);
                        addToAcceptList(model);
                        addUserToFriendContact(model);
                    }
                });
                holder.btn_decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Delete
                        deleteFriendRequest(model, true);
                    }
                });


            }

            @NonNull
            @Override
            public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_friend_request, viewGroup, false);
                return new FriendRequestViewHolder(itemView);
            }
        };

        adapter.startListening();
        recycler_all_user.setAdapter(adapter);

    }

    private void addUserToFriendContact(User model) {
        //Friend will add user
        DatabaseReference acceptList = FirebaseDatabase.getInstance()
                .getReference(Common.USER_INFORMATION)
                .child(model.getUid())
                .child(Common.ACCEPT_LIST);

        acceptList.child(model.getUid()).setValue(Common.loggedUser);
    }

    private void addToAcceptList(User model) {
        //User will add friend
        DatabaseReference acceptList = FirebaseDatabase.getInstance()
                .getReference(Common.USER_INFORMATION)
                .child(Common.loggedUser.getUid())
                .child(Common.ACCEPT_LIST);

        acceptList.child(model.getUid()).setValue(model);
    }

    @Override
    protected void onStop() {
        if (adapter != null)
            adapter.stopListening();
        if (searchAdapter != null)
            searchAdapter.stopListening();
        super.onStop();
    }

    private void deleteFriendRequest(final User model, final boolean isShowMessage) {
        DatabaseReference friendRequest = FirebaseDatabase.getInstance()
                .getReference(Common.USER_INFORMATION)
                .child(Common.loggedUser.getUid())
                .child(Common.FRIEND_REQUEST);

        friendRequest.child(model.getUid()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (isShowMessage)
                            Toast.makeText(FriendRequestActivity.this, "Removed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadSearchData() {
        Query query = FirebaseDatabase.getInstance().getReference().child(Common.USER_INFORMATION)
                .child(Common.loggedUser.getUid())
                .child(Common.FRIEND_REQUEST);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        searchAdapter = new FirebaseRecyclerAdapter<User, FriendRequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position, @NonNull User model) {

                holder.txt_user_email.setText(model.getEmail());
                holder.btn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteFriendRequest(model, false);
                        addToAcceptList(model);
                        addUserToFriendContact(model);
                    }
                });
                holder.btn_decline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Delete
                        deleteFriendRequest(model, true);
                    }
                });


            }

            @NonNull
            @Override
            public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_friend_request, viewGroup, false);
                return new FriendRequestViewHolder(itemView);
            }
        };

        searchAdapter.startListening();
        recycler_all_user.setAdapter(searchAdapter);
    }

    @Override
    public void onFirebaseLoadUserNameDone(List<String> lstEmail) {
        searchBar.setLastSuggestions(lstEmail);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
