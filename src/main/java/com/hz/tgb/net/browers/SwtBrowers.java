package com.hz.tgb.net.browers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * 挺好使的.....
 * java swing开发最简单的浏览器源代码
 * <p>依赖 org.eclipse.swt.win32.win32.x86_64_3.102.1.v20130827-2048.jar</p>
 *
 * @author www.zuidaima.com
 *
 */
public class SwtBrowers {//基于标签式的浏览器

    private volatile String newUrl = null;// 最新输入的链接

    private volatile boolean loadCompleted = false;//表示当前页面完全导入

    private volatile boolean openNewItem=false;//表示新的页面在新窗口中打开

 /*
  * 浏览器的当前标签参数
  */

    private TabItem tabItem_now;//当前标签项

    private Browser browser_now;//当前功能浏览器

    /*
     * 浏览器设置参数
     */
    private String homePage = "www.baidu.com";// 浏览器的首页

    /*
     * 浏览器外形布置
     */
    private Button button_back;//后退按钮

    private Button button_forward;//向前按钮

    private Button button_go;//前进按钮

    private Button button_stop;//停止按钮

    private Combo combo_address;// 地址栏

    private Browser browser_default = null;// 浏览窗口

    private ProgressBar progressBar_status;// 网页打开进度表，即页面导入情况栏

    private Label label_status;// 最终网页打开过程显示

    private TabFolder tabFolder;// Browser的容器

    private Composite composite_tool;// 工具栏区域

    private Composite composite_browser;// 浏览窗口区域

    private Composite composite_status;// 状态栏区域

    protected Display display;

    protected Shell shell_default;

    /**
     * Launch the application
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            SwtBrowers window = new SwtBrowers();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window
     */
    public void open() {
        display = Display.getDefault();
        shell_default = new Shell(display);
        createContents();

        shell_default.open();
        shell_default.layout();
        while (!shell_default.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    /**
     * Create contents of the window
     */
    protected void createContents() {
        shell_default.setSize(649, 448);
        shell_default.setText("浏览器");
        GridLayout gl_shell = new GridLayout();
        gl_shell.marginWidth = 0;// 组件与容器边缘的水平距离
        gl_shell.marginHeight = 0;// 组件与容器边缘的垂直距离
        gl_shell.horizontalSpacing = 0;// 组件之间的水平距离
        gl_shell.verticalSpacing = 0;// 组件之间的垂直距离
        shell_default.setLayout(gl_shell);

  /*
   * 创建浏览器界面
   */
        //createMenu();//没有实现
        createTool();
        createBrowser();
        createStatus();

  /*
   * 创建浏览器相关事件监听
   */
        runThread();
    }

    /*
     * 创建基本工具栏，不包括相关事件监听
     */
    private void createTool() {

        composite_tool = new Composite(shell_default, SWT.BORDER);
        // GridData()第一个参数是水平排列方式，第二个参数是垂直排列方式,第三个是水平抢占是否,第四个参数是垂直抢占是否
        GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_composite.heightHint = 30;// 高度和宽度
        gd_composite.widthHint = 549;
        composite_tool.setLayoutData(gd_composite);
        GridLayout fl_composite = new GridLayout();
        fl_composite.numColumns = 8;
        composite_tool.setLayout(fl_composite);

        button_back = new Button(composite_tool, SWT.NONE);
        button_back.setLayoutData(new GridData(27, SWT.DEFAULT));// 设置大小和格式
        button_back.setText("<-");

        button_forward = new Button(composite_tool, SWT.NONE);
        button_forward.setLayoutData(new GridData(24, SWT.DEFAULT));
        button_forward.setText("->");

        combo_address = new Combo(composite_tool, SWT.BORDER);
        final GridData gd_combo_3 = new GridData(SWT.FILL, SWT.LEFT, true,
                false);// 在窗口变化时，自动扩展水平方向的大小
        gd_combo_3.widthHint = 300;// 起始宽度
        gd_combo_3.minimumWidth = 50;// 设置最小宽度
        combo_address.setLayoutData(gd_combo_3);

        button_go = new Button(composite_tool, SWT.NONE);
        button_go.setLayoutData(new GridData(25, SWT.DEFAULT));
        button_go.setText("go");

        button_stop = new Button(composite_tool, SWT.NONE);
        button_stop.setLayoutData(new GridData(24, SWT.DEFAULT));
        button_stop.setText("stop");

        final Label label = new Label(composite_tool, SWT.SEPARATOR
                | SWT.VERTICAL);
        label.setLayoutData(new GridData(2, 17));

    }

    /*
     * 创建浏览器，不包括相关事件监听
     */
    private void createBrowser() {
        composite_browser = new Composite(shell_default, SWT.NONE);
        final GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true,
                true);// 充满窗口,且水平和垂直方向随窗口而变
        gd_composite.heightHint = 273;
        composite_browser.setLayoutData(gd_composite);
        GridLayout gl_composite = new GridLayout();
        gl_composite.marginHeight = 0;// 使组件上下方向容器
        gl_composite.marginWidth = 0;// 使组件左右方向占满容器
        composite_browser.setLayout(gl_composite);

        tabFolder = new TabFolder(composite_browser, SWT.NONE);
        final GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, true,
                true);
        gd_tabFolder.heightHint = 312;
        gd_tabFolder.widthHint = 585;
        tabFolder.setLayoutData(gd_tabFolder);

  /*
   * 为标签添加右键功能
   */
        tabFolder.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseUp(MouseEvent e) {
                if(e.button==3){//右键
                    Menu menu_itemRightMouse=new Menu(shell_default,SWT.POP_UP);
                    tabFolder.setMenu(menu_itemRightMouse);
                    MenuItem menuItem_itemClose=new MenuItem(menu_itemRightMouse,SWT.NONE);
                    menuItem_itemClose.setText("关闭当前标签");
                    menuItem_itemClose.addSelectionListener(new SelectionAdapter(){
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            if(tabFolder.getItemCount()!=1){//不是只存在一个标签的情况下
                                browser_now.dispose();
                                tabItem_now.dispose();
                                tabFolder.redraw();
                            }else{//只有一个标签
                                browser_now.setUrl(":blank");
                                browser_now.setText("");
                            }
                        }
                    });
                    MenuItem menuItem_itemCloseAll=new MenuItem(menu_itemRightMouse,SWT.NONE);
                    menuItem_itemCloseAll.setText("关闭所有标签");
                    menuItem_itemCloseAll.addSelectionListener(new SelectionAdapter(){
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            shell_default.close();
                        }
                    });
                }
            }
        });


        final TabItem tabItem_default = new TabItem(tabFolder, SWT.NONE);
        browser_default = new Browser(tabFolder, SWT.NONE);
        tabItem_default.setControl(browser_default);
        browser_default.setUrl(homePage);// 显示浏览器首页


  /*
   * 把初始化的标签置顶,选中
   */
        tabFolder.setSelection(tabItem_default);

    }

    /*
     * 创建浏览器底部状态栏，不包括相关事件监听
     */
    private void createStatus() {
        composite_status = new Composite(shell_default, SWT.NONE);
        final GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true,
                false);// 参数true使状态栏可以自动水平伸缩
        gd_composite.heightHint = 20;
        gd_composite.widthHint = 367;
        composite_status.setLayoutData(gd_composite);
        GridLayout gl_composite = new GridLayout();
        gl_composite.numColumns = 2;
        gl_composite.marginBottom = 5;
        composite_status.setLayout(gl_composite);

        label_status = new Label(composite_status, SWT.NONE);
        GridData gd_status = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_status.heightHint = 13;
        gd_status.widthHint = 525;
        label_status.setLayoutData(gd_status);

        progressBar_status = new ProgressBar(composite_status, SWT.BORDER
                | SWT.SMOOTH);
        progressBar_status.setLayoutData(new GridData(80, 12));
        progressBar_status.setVisible(false);// 打开过程初始不可见

    }

    private void runThread( ) {

  /*
   * 浏览器新标签前进、后退按钮的默认可用性为不可用
   */
        button_back.setEnabled(false);
        button_forward.setEnabled(false);

  /*
   * 获取浏览器的当前标签和功能Browser
   */
        tabItem_now=tabFolder.getItem(tabFolder.getSelectionIndex());
        browser_now=(Browser) tabItem_now.getControl();

  /*
   * 选中事件发生时，修改当前浏览器标签
   */
        tabFolder.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                TabItem temp=(TabItem) e.item;
                if(temp!=tabItem_now){//防止重选一个标签，预防多次触发相同事件
                    tabItem_now=temp;
                    browser_now=(Browser)tabItem_now.getControl();
                    //System.out.println("当前标签被修改了");//调试语句

     /*
      * 在相应的标签中，前进、后退按钮可用性是不一样的
      */
                    if(browser_now.isBackEnabled()){//后退按钮的可用性
                        button_back.setEnabled(true);
                    }else{
                        button_back.setEnabled(false);
                    }
                    if(browser_now.isForwardEnabled()){//前进按钮的可用性
                        button_forward.setEnabled(true);
                    }else{
                        button_forward.setEnabled(false);
                    }

                }
            }
        });

  /*
   * 添加浏览器的后退、向前、前进、停止按钮事件监听
   */
        button_back.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (browser_now.isBackEnabled()){//本次可后退
                    browser_now.back();
                    button_forward.setEnabled(true);//下次可前进，前进按钮可用
                    //System.out.println("可后退");//调试语句
                }
                if(!browser_now.isBackEnabled()){//下次不可后退，后退按钮不可用
                    button_back.setEnabled(false);
                }
            }
        });

        button_forward.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (browser_now.isForwardEnabled()){//本次可前进
                    browser_now.forward();
                    button_back.setEnabled(true);//后退按钮可用
                    //System.out.println("可向前");//调试语句
                }
                if(!browser_now.isForwardEnabled()){//下次不可前进，前进按钮不可用
                    button_forward.setEnabled(false);
                }
            }
        });

        button_stop.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                browser_now.stop();
            }
        });

        combo_address.addKeyListener(new KeyAdapter() {// 手动输入地址栏后，按回车键转到相应网址
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {//回车键触发事件
                    browser_now.setUrl(combo_address.getText());
                }
            }
        });

  /*
   * 1>在addOpenWindowListener()下的open()写入e.browser = browser_new情况下，导入新的超级链接,
   * 只有当点击页面上的链接,且链接不在新的页面打开时才会发生.
   * 2>在addOpenWindowListener()下的open()不写入e.browser = browser_new情况下，导入新的超级链接，
   * 只有当点击页面上的链接,且链接在新的页面打开时才会发生.
   * 除了以上两种外，当然还包括browser.back()、browser.forward()、browser.go()、browser.setUrl()发生时触发,
   * 但changing()在不写入e.browser = browser_new情况下,不被browser.setUrl()触发
   */
        browser_now.addLocationListener(new LocationAdapter() {
            @Override
            public void changing(LocationEvent e) {// 表示超级链接地址改变了
                if(openNewItem==false){//新的页面在同一标签中打开
                    button_back.setEnabled(true);//后退按钮可用,此句是后退按钮可用判定的逻辑开始点
                }
                //System.out.println("location_changing");// 调试语句
            }

            @Override
            public void changed(LocationEvent e) {// 找到了页面链接地址
                combo_address.setText(e.location);// 改变链接地址显示
    /*
     * 新的页面已经打开,browser的LocationListener已经监听完毕,openNewItem回复默认值
     */
                if(openNewItem==true){
                    openNewItem=false;
                }
                //System.out.println("location_changed");// 调试语句

            }

        });

  /*
   *  新的超级链接页面的导入的百分比,在导入新的页面时发生，此时链接地址已知
   */
        browser_now.addProgressListener(new ProgressAdapter() {
            @Override
            public void changed(ProgressEvent e) {//本事件不断发生于页面的导入过程中
                progressBar_status.setMaximum(e.total);// e.total表示从最开始页面到最终页面的数值
                progressBar_status.setSelection(e.current);
                if (e.current != e.total) {//页面还没完全导入
                    loadCompleted = false;
                    progressBar_status.setVisible(true);// 页面的导入情况栏可见
                } else {
                    loadCompleted = true;
                    progressBar_status.setVisible(false);// 页面导入情况栏不可见
                }
                //System.out.println("progress_changed");//调试语句

            }

            @Override
            public void completed(ProgressEvent arg0) {//发生在一次导入页面时,本监听器changed事件最后一次发生之前
                //System.out.println("progress_completed");//调试语句
            }
        });

  /*
   *  获取页面内容过程,文字显示addProgressListener()过程,同时还能检测到已打开页面的存在的超级链接,就是用给功能来获取
   *  新的链接地址的
   */
        browser_now.addStatusTextListener(new StatusTextListener() {
            public void changed(StatusTextEvent e) {
                if (loadCompleted == false) {
                    label_status.setText(e.text);
                } else {
                    newUrl = e.text;//页面导入完成，捕捉页面上可能打开的链接
                }
                //System.out.println("statusText_changed");//调试语句
            }
        });

  /*
   * 显示页面的提示语句，在新的页面导入时发生
   */
        browser_now.addTitleListener(new TitleListener() {
            public void changed(TitleEvent e) {
                shell_default.setText(e.title);
                if (e.title.length() > 3) {//显示当前页面提示字符在标签上
                    tabItem_now.setText(e.title.substring(0, 3) + "..");
                } else {
                    tabItem_now.setText(e.title);
                }
                tabItem_now.setToolTipText(e.title);//标签显示提示符
            }
        });

  /*
   * 打开新的页面，当前打开页面新的链接需要在新的窗口页面打开时发生.addOpenWindowListener下open()中的一句
   * e.browser = browser_new;关键部分.联系addOpenWindowListener、addVisibilityWindowListener
   * 和addDisposeListener的值传递枢纽
   */
        browser_now.addOpenWindowListener(new OpenWindowListener() {// 在当前页面中打开点击的链接页面
            public void open(WindowEvent e) {
                Browser browser_new = new Browser(tabFolder, SWT.NONE);
                TabItem tabItem_new = new TabItem(tabFolder, SWT.NONE);
                tabItem_new.setControl(browser_new);
                tabFolder.setSelection(tabItem_new);//新打开的页面标签置顶
                tabFolder.redraw();//刷新容器
                browser_new.setUrl(newUrl);//新标签中设置新的链接地址
                openNewItem=true;//新的页面在新的标签中打开

      /*
       * 关键部分,告知新的页面由browser_new打开,只要实现这句就不会弹出操作系统默认的浏览器了
       */
                e.browser = browser_new;
                //System.out.println("OpenWindowListener_open");//调试语句

      /*
       * 为浏览器新的标签添加事件监听
       */
                display.syncExec(new Runnable(){
                    public void run() {
                        runThread();
                    }
                });


            }
        });

  /*
   * 浏览器关闭事件,关闭当前功能浏览器,不然的话浏览器主窗口关闭了，还有进程在运行
   */
        browser_now.addCloseWindowListener(new CloseWindowListener(){
            public void close(WindowEvent e) {
                browser_now.dispose();
            }
        });

    }
}