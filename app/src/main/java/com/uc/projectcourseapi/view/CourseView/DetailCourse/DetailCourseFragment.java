package com.uc.projectcourseapi.view.CourseView.DetailCourse;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uc.projectcourseapi.R;
import com.uc.projectcourseapi.helper.SharedPreferenceHelper;
import com.uc.projectcourseapi.model.Course;
import com.uc.projectcourseapi.view.CourseView.CourseViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCourseFragment extends Fragment {
    Toolbar toolbar;
    TextView detailCourseCode, detailCourseTitle, detailCourseLecturer, detailCourseSks, detailCourseDesc;

    private static final String TAG = "DetailCourseFragment";

    private CourseViewModel courseViewModel;
    private SharedPreferenceHelper helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailCourseFragment newInstance(String param1, String param2) {
        DetailCourseFragment fragment = new DetailCourseFragment();
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
        return inflater.inflate(R.layout.fragment_detail_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar_main);
        toolbar.setTitle("Detail Course");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        detailCourseCode = view.findViewById(R.id.detail_course_code);
        detailCourseTitle = view.findViewById(R.id.detail_course_title);
        detailCourseLecturer = view.findViewById(R.id.detail_course_lecturer);
        detailCourseSks = view.findViewById(R.id.detail_course_sks);
        detailCourseDesc = view.findViewById(R.id.detail_course_description);

        courseViewModel = new ViewModelProvider(getActivity()).get(CourseViewModel.class);
        helper = SharedPreferenceHelper.getInstance(requireActivity());
        courseViewModel.init(helper.getAccessToken());
        String code = getArguments().getString("course_code");
        Log.d(TAG, "course_code: "+code);
        courseViewModel.getCoursesDetail(code);
        courseViewModel.getResultCourseDetail().observe(getActivity(), showCourseDetail);
    }

    private Observer<Course> showCourseDetail = new Observer<Course>() {
        @Override
        public void onChanged(Course course) {
            Log.d(TAG, "course: "+course);
            Course.Courses resultCourse= course.getCourses().get(0);
            Log.d(TAG, "resultCourse: "+resultCourse);
            if (course == null){
                detailCourseCode.setText("Unknown");
                detailCourseTitle.setText("Unknown");
                detailCourseLecturer.setText("Unknown");
                detailCourseSks.setText("Unknown");
                detailCourseDesc.setText("Unknown");
            }else {
                String codeCourse = resultCourse.getCourse_code();
                String title = resultCourse.getCourse_name();
                String lecturer = resultCourse.getLecturer();
                int sks = resultCourse.getNumber_sks();
                String desc = resultCourse.getDescription();
                detailCourseCode.setText(codeCourse);
                detailCourseTitle.setText(title);
                detailCourseLecturer.setText(lecturer);
                detailCourseSks.setText(String.valueOf(sks));
                detailCourseDesc.setText(desc);
            }
        }
    };
}