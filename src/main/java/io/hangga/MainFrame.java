package io.hangga;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class MainFrame extends JFrame {

    //static JLabel l;
    static JFileChooser outputChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
    private static String template = Invigen.template;


    MainFrame() {
    }

    public static void main(String[] args) {
        //void init(){
        // frame to contains GUI elements
        JFrame frame = new JFrame("InviGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        JButton btnChooseTemplate = new JButton("Pilih Template");
        JLabel lblTemplate = new JLabel("Template : " + Invigen.template);
        topPanel.add(lblTemplate, BorderLayout.LINE_START);
        topPanel.add(btnChooseTemplate, BorderLayout.CENTER);

        pane.add(topPanel);

        JLabel lblTulisNama = new JLabel("Tulis Nama-nama Peserta (pisahkan dengan koma , )");
        JPanel topSubPanel = new JPanel();
        topSubPanel.add(lblTulisNama, BorderLayout.LINE_START);
        topSubPanel.add(new JPanel(), BorderLayout.LINE_END);
        topSubPanel.setSize(500, 100);
        pane.add(topSubPanel);


        JTextArea ta = new JTextArea(20,4);

        JScrollPane scroll = new JScrollPane(ta);
        scroll.setSize(new Dimension(600, 600));

        pane.add(scroll, BorderLayout.LINE_START);
        // just sample
        ta.setText("Bambang, Budiman, Paiman, Pardiman, Ngattijan, Suherman, Wakijan, WAgiman, Wagiran, TTugiran, " +
                "Sangiran, PPoniran, Ponimin, Ngatimin, Ngadimin, Wagimin, Ponirin, Ngahidin, Sugimin, Wagimin, " +
                "Wagirin, Tugirin, Wahidin, Sagirin, haa,habakukg,habarova,habuchi,hacer,hacernur,hachamo,hachiya,hacienda," +
                "hack,hacker,hacker123,hacluster,hadarnev,haden,hades,hadoop,HADOOP,hadoop1,hadoop2,hadoop3,hadoop4,hadoop5," +
                "hadoopuser,hafize,haga,hagaiba,hagen,hagiwara,hahaha,hahulinm,haibis,hailey,haim,haiping,haisou,haitac,hajnal,hakan," +
                "hakanb,hakanolkan,hakki,hakusyo,hal,halasz,haldaemon,haldeamon,hale,half,halic,halina,hall,hallie,hallo,halt," +
                "halt@vultr.com,hama,hamada,hamakin,hamdik,hamdullah,hamel,hamilton,hamlet,hammer,hampton,hamster,hamukumi,hamzaabu," +
                "han,hana,hance,hand,handball,hande,handel,hanen,hang,hank,hanke,hanna,hannah,hanne,hannelore,hannes,hanoop,hans," +
                "hanshoku,hansolo,hanson,hanst,hansung,haoki,happinessm,happy,happyart,happyday,happylife,happymarketing,happytv,hara," +
                "harada,haraday,haraiin,harakawa,harald,haramaki,hardale,hardcore,harder,hardik,hardya,hargens,hari,harigaya,harima," +
                "harish,harisson,harit,harkesh,harley,harm,harmeet,harmless,harmonie,harold,harpreet,harriet,harriett,harris,harrison," +
                "harry,harrypotter,harsh,harshad,harshita,hart,hartmann,hartnett,harto,hartwin,harunecik,haruperic,harvey,has,hasan," +
                "hasans,hasegawa,hashimoto,hasin,hass,hassan,hassanass,hat,hatchery,hate,hatice,Hatice,haticedrsn,haticetr,hatori," +
                "hatsutori,hatton,hau,haven,havvackr,hawaii,hawk,hawkeye,haxor,hayagawa,hayakawa,hayashi,hayden,hayley,haylie,hayrisari," +
                "hazel,hbarry,hbase,hbbnet1qaz,hcaj,hcat,hcharan,hckocak,hcl,hcldev,hd,hdd,hdduser,hdelrio,hdfs,hduser,he,head,header," +
                "headmaster,health,hear,heart,hearts,heath,heather,heaven,hebertc,hector,hedb,hedwig,hegai,heida,heidi,heidrun,heike," +
                "heilwig,heino,heinz,heironymus,helen,helena,helene,helenl,helga,helge,hell,hella,hellen,hellena,hello,HELLO,helmut," +
                "heloise,help,helpdesk,helpdesk@uscompall.com,helper,helpme,help@uscompall.com,help@vultr.com,helton,hemant," +
                "hendrix,heng,henley,henning,henny,henri,henrik,henrike,henrique,henry,henrym,henseler,hera,herb,herbert,herbertc," +
                "hercule,hercules,hergun,heriberto,herman,hermann,hermes,hermesfiling,hernandez,hero,herr,herrera,herschel,herschell," +
                "hershey,hertha,hessym,hex,hexin,hfairweather,hg,hgd,hgusr,hh,hha,hhcrew,hhe,hhh,hhhh,hhhhh,hhj,hhxie,hhy,hi,hi3518,hibi," +
                "hibiz,hicham,Hicksville,hid,hidai,hidaka,hidden-user,hideyuki,hiedi,higashi,highspeed,higuchi,hilabash,hilary,hilda,hildac," +
                "hilde,hildebert,hildebrand,hildegarde,hilk,hill,hillary,hiltrud,him,himani,himanshu,himanthd,hines,hinfo,hinrich,hiperg," +
                "hippodrome,hirai,hirano,hirata,hiren,hirenj,hiro,hiroaki,hiroki,HIROKI,hiromi,hirose,hirosi,hiroya,hisano,history,hitch," +
                "hitech,hitler,hive,hivemq,hiwi,hjoshi,hjt,hjz,hk,hkj,hknkr,hko,hky,hl2dm,hl2dmserver,hldmc,hldmcserver,hlds,hlima,hlouthan," +
                "hlse,hlu,hlybova,hlyky,hmkim,hmlshare,hmltest,hmoreira,hmsftp,hndnyrdm,hnftp_root,ho,hoandy,hobbit,hobsons,hockey,hod,hoda," +
                "hohoho,hol,hola,holding,holland,holley,hollie,holly,hollywood,holoodabu,holy,holywar,hom,home,homebk,homebrew,home-modem," +
                "homepage,homepage123,homepage@123,homer,homo,honaga,honda,honestm,honests,honestym,honey,honeypot,honeyridge,hong,hongou," +
                "honma,honoriat,honorine,honsya,hoo,hood,hoops,hootie,hoover,hope,hopeb,hopewell,hopkins,horace,horatiu,horde,hori,horia," +
                "horigome,horizon,hornet,hornets,horse,horses,hortensia,hortschitz,hosakof,hosakoh,hoshi,hoshide,hoshino,hosokawa,hosoki," +
                "hosomi,hospital,hossain,host,+host,hosting,hostmaster,hostnet,hosts,hostworks,hot,hotdog,hotel,hotels,hotman,hotrod,houjin," +
                "hourai,house,houseenkhl,housou,houston,hout,howard,howardm,howe,howie,hozumi,hp,hpadmin,hpardede,hpcadmin,hplesk,hplip,hpreform," +
                "hps,hptest,hqitsm,hr,hrahman,hrd,hregistry,hrhatwar,hrishav,hrojas,hrtuser,hsa:hsadb,hsano,hsantelices,hscroot,hsherman,hsieh,hsin," +
                "hsqldb,hsrtcskn,hsu,hsubieta,hswami,htdocs,htm,html,html@vultr.com,htp,htshine,htt,http,HTTP,http123,httpadmin,httpd,httpd2,httpdocs," +
                "httpd@vultr.com,httpfs,httpguest,httpuser,http@vultr.com,hu,hua,huang,huawei,Huawei,!!Huawei,huawei007SH,huawei@123,hubei,hubertus," +
                "hudix,hudson,huercal,huey,hug,hugo,hugues,hui,huisan,humbert,humberto,humeyra,humeyrac,humphrey,hundsun,hung,hunny,hunt,hunter,hunter-c," +
                "hunting,huppert,huregochi,hurnberto,hurtworld2,huseyin,huskers,hussam,hussein,husseinasl,hussnyazz,hutami,hvelasquez,hvngoc,hwamic,hwang," +
                "hwangsan,hwkim,hxli,hy,hyaldiz,hydra,hyoungwoo,hyperic,hzltrkdnmz,hzsnoc,hzuleta,i,iadmin,iakkas,ian,iasi,ibabalik,ibandro,ibank," +
                "ibankdebit,ibascunan,ibbo,ibelostotskiy,ibenkdebit,ibm,ibmadm,ibmadmin,ibmum,ibmuser,ibolya,ibrahim,ibrahimseh,ibsevimli,ibustos,icalizaya," +
                "icbeu,ice,icecast,icecast2,icecream,icehero,iceman,icepack,ices,iceuser,ichertok,ichikawa,ichikawak,icici,icinga,ICINGA,icls,icnanker,icons," +
                "icosftp,icp,icthelpdesk,id,idanavi,idea,ideguchi,ident,identd,ident@vultr.com,idiot,idris,ids2,iduran,ieda,iesse,ietune,if,ifanw,ifconfig,ifreizon," +
                "iftitahu,ifurkan,ifxjava,igadam,iganatiom,igibson,igkim,ignac,ignace,ignacio,ignas,ignat,ignatiom,ignatova,ignatyukvi,ignatz," +
                "ignore,igor,igorpot,igosyogi,igrek,iguana,igutierrez,ihc,iheroglu,ihira,ihqmoddnom,ihsan,ihunter," +
                "nagata,nagihan,nagios,NAGIOS,nagios1,NAGIOS1,nagios2,nagios3,nagios4,nagios5,nagiosadmin");




        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);


        JButton btnGenerate = new JButton("Generate");
        JPanel bottomPanel = new JPanel();
        bottomPanel.setSize(new Dimension(600, 50));
        //bottomPanel.setBackground(Color.BLUE);
        bottomPanel.add(progressBar);
        bottomPanel.add(btnGenerate, BorderLayout.LINE_END);
        pane.add(bottomPanel);

        btnChooseTemplate.addActionListener(actionEvent -> {
            JFileChooser fileTemplateChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());

            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));

            if (fileTemplateChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                template = fileTemplateChooser.getSelectedFile().getAbsolutePath();
                lblTemplate.setText("Template : " + template);
            }
        });

        btnGenerate.addActionListener(actionEvent -> {

            if (ta.getText().trim().length() == 0){
                showInfo(frame);
                return;
            }
            String[] arrNames = ta.getText().trim().split(",");
            //int max = arrNames.length;

            progressBar.setMaximum(arrNames.length);

            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));

            if (outputChooser.getSelectedFile() != null) {
                progressBar.setVisible(true);
                new Generator().setPath(template, outputChooser.getSelectedFile().getAbsolutePath(), arrNames.length, new OnCopying() {
                    @Override
                    public void OnCopyProgress(int progress, String status) {
                        progressBar.setString("Tunggu...");
                        progressBar.setValue(progress);
                    }

                    @Override
                    public void OnCopyFinish(String copyOutput) {
                        new DocProcessor().replaceName(arrNames, copyOutput, outputChooser.getSelectedFile().getAbsolutePath(), new OnWriting() {
                            @Override
                            public void onProgress(int progress, String status) {
                                progressBar.setString("Menulis Nama...");
                                progressBar.setValue(progress);
                            }

                            @Override
                            public void onFinished() {
                                progressBar.setVisible(false);
                            }
                        }).execute();
                    }

                    @Override
                    public void OnError(String errMsg) {

                    }
                }).execute();

            } else {
                outputChooser.setSelectedFile(new File("output-invigen.docx"));

                if (outputChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {

                    progressBar.setVisible(true);
                    new Generator().setPath(template, outputChooser.getSelectedFile().getAbsolutePath(), arrNames.length, new OnCopying() {
                        @Override
                        public void OnCopyProgress(int progress, String status) {
                            progressBar.setString("Tunggu...");
                            progressBar.setValue(progress);
                        }

                        @Override
                        public void OnCopyFinish(String copyOutput) {
                            new DocProcessor().replaceName(arrNames, copyOutput, outputChooser.getSelectedFile().getAbsolutePath(), new OnWriting() {
                                @Override
                                public void onProgress(int progress, String status) {
                                    progressBar.setValue(progress);
                                    progressBar.setString("Menulis Nama...");
                                }

                                @Override
                                public void onFinished() {
                                    progressBar.setVisible(false);
                                }
                            }).execute();
                        }

                        @Override
                        public void OnError(String errMsg) {

                        }
                    }).execute();

                }
            }

        });
        frame.setVisible(true);
    }


    static void showInfo(JFrame frame){
        JOptionPane.showMessageDialog(frame,
                "Ketik nama-nama dan pisahkan dengan [,]",
                "Berhasil",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
