package eu.kanade.mangafeed.ui.viewer.base;

import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.util.List;

import eu.kanade.mangafeed.R;
import eu.kanade.mangafeed.data.models.Page;
import eu.kanade.mangafeed.ui.activity.ReaderActivity;
import eu.kanade.mangafeed.ui.adapter.ReaderPageAdapter;
import eu.kanade.mangafeed.widget.ReaderViewPager;

public abstract class ViewPagerViewer extends BaseViewer {

    protected ReaderViewPager viewPager;
    protected ReaderPageAdapter adapter;

    public ViewPagerViewer(ReaderActivity activity, FrameLayout container) {
        super(activity, container);
        activity.getLayoutInflater().inflate(R.layout.viewer_viewpager, container);

        adapter = new ReaderPageAdapter(activity.getSupportFragmentManager());
        viewPager = (ReaderViewPager) activity.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ReaderViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                updatePageNumber();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnChapterBoundariesOutListener(new ReaderViewPager.OnChapterBoundariesOutListener() {
            @Override
            public void onFirstPageOutEvent() {
                onFirstPageOut();
            }

            @Override
            public void onLastPageOutEvent() {
                onLastPageOut();
            }
        });
    }

    @Override
    public int getTotalPages() {
        return adapter.getCount();
    }

    @Override
    public void onPageListReady(List<Page> pages) {
        adapter.setPages(pages);
        updatePageNumber();
    }

    @Override
    public void onImageReady(Page page) {
        adapter.replacePage(getPosFromPage(page), page);
    }

    @Override
    public boolean onImageTouch(MotionEvent motionEvent) {
        return viewPager.onImageTouch(motionEvent);
    }

    public abstract void onFirstPageOut();
    public abstract void onLastPageOut();

}