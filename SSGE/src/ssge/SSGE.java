/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssge;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;

/**
 *
 * @author Lociatus
 */
public class SSGE extends javax.swing.JFrame {

    private IOSaveGameFile sgf;
    private StellarisSaveGameObject saveGame;
    private List<DropDownItem> empireList;
    private List<DropDownItem> speciesList;
    private List<DropDownItem> allCountryModifierList;
    private DefaultListModel speciesTraitsListModell;
    private DefaultListModel countryCivicsListModell;
    private DefaultListModel countryModifierListModell;
    private SaveGameParameter saveGameParameter;

    /**
     * Creates new form SSGE
     */
    public SSGE() {
        this.sgf = new IOSaveGameFile();
        this.empireList = new ArrayList();
        this.speciesList = new ArrayList();
        this.allCountryModifierList = new ArrayList();
        this.speciesTraitsListModell = new DefaultListModel();
        this.countryCivicsListModell = new DefaultListModel();
        this.countryModifierListModell = new DefaultListModel();
        try {
            URL url = getClass().getResource("parameter.xml");
            this.saveGameParameter = new SaveGameParameter(url.getPath());
        } catch(Exception e) {
            System.out.println("parameter.xml not found\n e="+e);
        }
        initComponents();
    }
    
    private void updateEmpireRess() {   
        this.jTextFieldEnergy.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "energy"));
        this.jTextFieldMinerals.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "minerals"));
        this.jTextFieldFood.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "food"));
        this.jTextFieldInfluence.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "influence"));
        this.jTextFieldUnity.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "unity"));
        this.jTextFieldAlloys.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "alloys"));
        this.jTextFieldConsumerGoods.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "consumer_goods"));
        this.jTextFieldVolatileMotes.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "volatile_motes"));
        this.jTextFieldExoticGases.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "exotic_gases"));
        this.jTextFieldRareCrystals.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "rare_crystals"));
        this.jTextFieldLivingMetal.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_living_metal"));
        this.jTextFieldZro.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_zro"));
        this.jTextFieldDarkMatter.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_dark_matter"));
        this.jTextFieldNanites.setText(this.saveGame.getRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "nanites"));
    }
    
    private void updateGameInfo() {
        this.jCheckBoxIronMan.setSelected(this.saveGame.isIronman());
        this.jTextFieldVersion.setText(this.saveGame.getVersion());
        this.jTextFieldName.setText(this.saveGame.getName());
        this.jTextFieldDate.setText(this.saveGame.getMetaDate());
        this.jTextAreaDLCS.setText(this.saveGame.getDLCs());
    }
    
    private void updateCountryCivics() {                                                        
        try { 
            this.countryCivicsListModell.clear();
            String cid = ((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId();
            Set<String> s = new HashSet<>();    
            s.addAll(this.saveGameParameter.getCivicsList());
            s.addAll(this.saveGame.getCountryCivicList(cid));
            List<String> cList = new ArrayList<>(s); 
            Collections.sort(cList);
            cList.forEach(c -> this.countryCivicsListModell.addElement(c));            
            this.saveGame.getCountryCivicList(cid).forEach(c -> this.jListCivics.setSelectedValue(c, true));
        } catch (Exception e) {
        }
    }
    
    private void updateCountryModifierList() {
        this.countryModifierListModell.clear();
        String cid = ((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId();
        List<String> mList = this.saveGame.getCountryModifierList(cid).stream().map(i -> "("+i.getId()+" Days) "+i.getText()).collect(Collectors.toCollection(ArrayList::new));
        mList.forEach(m -> this.countryModifierListModell.addElement(m));
        List<String> dmList = this.saveGame.getCountryModifierList(cid)
                .stream()
                .filter(m -> !this.saveGameParameter.getEmpireModifier().contains(m.getText()))
                .map(m -> m.getText())
                .collect(Collectors.toCollection(ArrayList::new));
        dmList.forEach(m -> this.allCountryModifierList.add(new DropDownItem(m,"")));
        this.jComboBoxSelectedCountryModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new Vector(this.allCountryModifierList)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox16 = new javax.swing.JCheckBox();
        jTabbedPanel1 = new javax.swing.JTabbedPane();
        jPanelFile = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldSavegameFile = new javax.swing.JTextField();
        jButtonFindSavegameFile = new javax.swing.JButton();
        jCheckBoxUseTextFile = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldMetaFile = new javax.swing.JTextField();
        jTextFieldGamestateFile = new javax.swing.JTextField();
        jButtonFindMetaFile = new javax.swing.JButton();
        jButtonFindGamestateFile = new javax.swing.JButton();
        jCheckBoxOtherSaveFile = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNewFile = new javax.swing.JTextField();
        jButtonFindNewFile = new javax.swing.JButton();
        jButtonOpen = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jPanelGame = new javax.swing.JPanel();
        jCheckBoxIronMan = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldVersion = new javax.swing.JTextField();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldDate = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDLCS = new javax.swing.JTextArea();
        jPanelEmpire = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxEmpire = new javax.swing.JComboBox<>();
        jTabbedPaneEmpire = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldEnergy = new javax.swing.JTextField();
        jTextFieldMinerals = new javax.swing.JTextField();
        jTextFieldFood = new javax.swing.JTextField();
        jTextFieldInfluence = new javax.swing.JTextField();
        jTextFieldUnity = new javax.swing.JTextField();
        jTextFieldAlloys = new javax.swing.JTextField();
        jTextFieldConsumerGoods = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldVolatileMotes = new javax.swing.JTextField();
        jTextFieldExoticGases = new javax.swing.JTextField();
        jTextFieldRareCrystals = new javax.swing.JTextField();
        jTextFieldLivingMetal = new javax.swing.JTextField();
        jTextFieldZro = new javax.swing.JTextField();
        jTextFieldDarkMatter = new javax.swing.JTextField();
        jTextFieldNanites = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPaneModifier = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListCountryModifierList = new javax.swing.JList<>();
        jComboBoxSelectedCountryModifier = new javax.swing.JComboBox<>();
        jCheckBoxCountryActive = new javax.swing.JCheckBox();
        jTextFieldCountryModifierDays = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCivics = new javax.swing.JList<>();
        jButtonUpdateCivics = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jComboBoxSpecies = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListSpeciesTrails = new javax.swing.JList<>();
        jButtonSpeciesUpdateChanges = new javax.swing.JButton();

        jCheckBox16.setText("jCheckBox16");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPanel1.setEnabled(false);

        jLabel1.setText("Savegame File");

        jTextFieldSavegameFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSavegameFileActionPerformed(evt);
            }
        });

        jButtonFindSavegameFile.setText("Find");
        jButtonFindSavegameFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindSavegameFileActionPerformed(evt);
            }
        });

        jCheckBoxUseTextFile.setText("Use Text Files");
        jCheckBoxUseTextFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxUseTextFileActionPerformed(evt);
            }
        });
        jCheckBoxUseTextFile.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCheckBoxUseTextFilePropertyChange(evt);
            }
        });

        jLabel2.setText("meta File");

        jLabel3.setText("gamestate File");

        jTextFieldMetaFile.setEnabled(false);

        jTextFieldGamestateFile.setEnabled(false);

        jButtonFindMetaFile.setText("Find");
        jButtonFindMetaFile.setEnabled(false);
        jButtonFindMetaFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindMetaFileActionPerformed(evt);
            }
        });

        jButtonFindGamestateFile.setText("Find");
        jButtonFindGamestateFile.setEnabled(false);
        jButtonFindGamestateFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindGamestateFileActionPerformed(evt);
            }
        });

        jCheckBoxOtherSaveFile.setText("other Save File");
        jCheckBoxOtherSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOtherSaveFileActionPerformed(evt);
            }
        });

        jLabel4.setText("New File");

        jTextFieldNewFile.setEnabled(false);
        jTextFieldNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNewFileActionPerformed(evt);
            }
        });

        jButtonFindNewFile.setText("Find");
        jButtonFindNewFile.setEnabled(false);
        jButtonFindNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindNewFileActionPerformed(evt);
            }
        });

        jButtonOpen.setText("Open");
        jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenActionPerformed(evt);
            }
        });

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFileLayout = new javax.swing.GroupLayout(jPanelFile);
        jPanelFile.setLayout(jPanelFileLayout);
        jPanelFileLayout.setHorizontalGroup(
            jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFileLayout.createSequentialGroup()
                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFileLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelFileLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldGamestateFile, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonFindGamestateFile))
                            .addGroup(jPanelFileLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(48, 48, 48)
                                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxOtherSaveFile)
                                    .addComponent(jTextFieldNewFile, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonFindNewFile))
                            .addGroup(jPanelFileLayout.createSequentialGroup()
                                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxUseTextFile)
                                    .addGroup(jPanelFileLayout.createSequentialGroup()
                                        .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldSavegameFile, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                                            .addComponent(jTextFieldMetaFile))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButtonFindSavegameFile)
                                            .addComponent(jButtonFindMetaFile)))))))
                    .addGroup(jPanelFileLayout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(jButtonOpen))
                    .addGroup(jPanelFileLayout.createSequentialGroup()
                        .addGap(319, 319, 319)
                        .addComponent(jButtonSave)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanelFileLayout.setVerticalGroup(
            jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFileLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldSavegameFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFindSavegameFile))
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxUseTextFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldMetaFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFindMetaFile))
                .addGap(18, 18, 18)
                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldGamestateFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFindGamestateFile))
                .addGap(18, 18, 18)
                .addComponent(jButtonOpen)
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxOtherSaveFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldNewFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFindNewFile))
                .addGap(18, 18, 18)
                .addComponent(jButtonSave)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        jTabbedPanel1.addTab("File", jPanelFile);

        jCheckBoxIronMan.setText("Ironman");
        jCheckBoxIronMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxIronManActionPerformed(evt);
            }
        });

        jLabel5.setText("Version");

        jLabel6.setText("Name");

        jLabel7.setText("Date");

        jLabel8.setText("DLCs");

        jTextFieldVersion.setEditable(false);

        jTextFieldName.setEditable(false);

        jTextFieldDate.setEditable(false);

        jTextAreaDLCS.setEditable(false);
        jTextAreaDLCS.setColumns(20);
        jTextAreaDLCS.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDLCS);

        javax.swing.GroupLayout jPanelGameLayout = new javax.swing.GroupLayout(jPanelGame);
        jPanelGame.setLayout(jPanelGameLayout);
        jPanelGameLayout.setHorizontalGroup(
            jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGameLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelGameLayout.createSequentialGroup()
                        .addGroup(jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jCheckBoxIronMan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldVersion)
                            .addComponent(jTextFieldName)
                            .addComponent(jTextFieldDate)))
                    .addGroup(jPanelGameLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(440, Short.MAX_VALUE))
        );
        jPanelGameLayout.setVerticalGroup(
            jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGameLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jCheckBoxIronMan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(148, Short.MAX_VALUE))
        );

        jTabbedPanel1.addTab("Game", jPanelGame);

        jLabel9.setText("Empire");

        jComboBoxEmpire.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {" "}));
        jComboBoxEmpire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEmpireActionPerformed(evt);
            }
        });

        jTabbedPaneEmpire.setEnabled(false);

        jLabel10.setText("Energy");

        jLabel11.setText("Minerals");

        jLabel12.setText("Food");

        jLabel13.setText("Influence");

        jLabel14.setText("Unity");

        jLabel15.setText("Alloys");

        jLabel16.setText("Consumer Goods");

        jTextFieldEnergy.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldEnergy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldEnergyFocusLost(evt);
            }
        });
        jTextFieldEnergy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEnergyActionPerformed(evt);
            }
        });

        jTextFieldMinerals.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldMinerals.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldMineralsFocusLost(evt);
            }
        });
        jTextFieldMinerals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMineralsActionPerformed(evt);
            }
        });

        jTextFieldFood.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldFood.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldFoodFocusLost(evt);
            }
        });
        jTextFieldFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFoodActionPerformed(evt);
            }
        });

        jTextFieldInfluence.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldInfluence.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldInfluenceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldInfluenceFocusLost(evt);
            }
        });
        jTextFieldInfluence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldInfluenceActionPerformed(evt);
            }
        });

        jTextFieldUnity.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldUnity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldUnityFocusLost(evt);
            }
        });
        jTextFieldUnity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUnityActionPerformed(evt);
            }
        });

        jTextFieldAlloys.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAlloys.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldAlloysFocusLost(evt);
            }
        });
        jTextFieldAlloys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAlloysActionPerformed(evt);
            }
        });

        jTextFieldConsumerGoods.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldConsumerGoods.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldConsumerGoodsFocusLost(evt);
            }
        });
        jTextFieldConsumerGoods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldConsumerGoodsActionPerformed(evt);
            }
        });

        jLabel17.setText("Volatile Motes");

        jLabel18.setText("Exotic Gases");

        jLabel19.setText("Rare Crystals");

        jLabel20.setText("Living Metal");

        jLabel21.setText("Zro");

        jLabel22.setText("Dark Matter");

        jLabel23.setText("Nanites");

        jTextFieldVolatileMotes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldVolatileMotes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldVolatileMotesFocusLost(evt);
            }
        });
        jTextFieldVolatileMotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldVolatileMotesActionPerformed(evt);
            }
        });

        jTextFieldExoticGases.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldExoticGases.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldExoticGasesFocusLost(evt);
            }
        });
        jTextFieldExoticGases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldExoticGasesActionPerformed(evt);
            }
        });

        jTextFieldRareCrystals.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldRareCrystals.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldRareCrystalsFocusLost(evt);
            }
        });
        jTextFieldRareCrystals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRareCrystalsActionPerformed(evt);
            }
        });

        jTextFieldLivingMetal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldLivingMetal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldLivingMetalFocusLost(evt);
            }
        });
        jTextFieldLivingMetal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLivingMetalActionPerformed(evt);
            }
        });

        jTextFieldZro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldZro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldZroFocusLost(evt);
            }
        });
        jTextFieldZro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldZroActionPerformed(evt);
            }
        });

        jTextFieldDarkMatter.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDarkMatter.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldDarkMatterFocusLost(evt);
            }
        });
        jTextFieldDarkMatter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDarkMatterActionPerformed(evt);
            }
        });

        jTextFieldNanites.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldNanites.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldNanitesFocusLost(evt);
            }
        });
        jTextFieldNanites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNanitesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel10))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldEnergy, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(jTextFieldAlloys)
                    .addComponent(jTextFieldUnity)
                    .addComponent(jTextFieldInfluence)
                    .addComponent(jTextFieldMinerals)
                    .addComponent(jTextFieldFood)
                    .addComponent(jTextFieldConsumerGoods))
                .addGap(92, 92, 92)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldVolatileMotes)
                    .addComponent(jTextFieldExoticGases)
                    .addComponent(jTextFieldRareCrystals)
                    .addComponent(jTextFieldLivingMetal)
                    .addComponent(jTextFieldZro)
                    .addComponent(jTextFieldDarkMatter)
                    .addComponent(jTextFieldNanites, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldEnergy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldVolatileMotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldMinerals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldExoticGases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jTextFieldRareCrystals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldInfluence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldLivingMetal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldUnity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldZro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldAlloys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jTextFieldDarkMatter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldConsumerGoods, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jTextFieldNanites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneEmpire.addTab("Ressource", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPaneEmpire.addTab("Research", jPanel2);

        jScrollPaneModifier.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneModifier.setToolTipText("");

        jListCountryModifierList.setModel(this.countryModifierListModell);
        jListCountryModifierList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListCountryModifierList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListCountryModifierListValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jListCountryModifierList);

        jComboBoxSelectedCountryModifier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {" "}));
        jComboBoxSelectedCountryModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSelectedCountryModifierActionPerformed(evt);
            }
        });

        jCheckBoxCountryActive.setText("Active");
        jCheckBoxCountryActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxCountryActiveActionPerformed(evt);
            }
        });

        jTextFieldCountryModifierDays.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldCountryModifierDaysFocusLost(evt);
            }
        });
        jTextFieldCountryModifierDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCountryModifierDaysActionPerformed(evt);
            }
        });

        jLabel26.setText("Days");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxCountryActive, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSelectedCountryModifier, 0, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCountryModifierDays, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxCountryActive)
                            .addComponent(jComboBoxSelectedCountryModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCountryModifierDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPaneModifier.setViewportView(jPanel6);

        jTabbedPaneEmpire.addTab("Modifier", jScrollPaneModifier);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jListCivics.setModel(this.countryCivicsListModell);
        jListCivics.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                }
                else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        jScrollPane2.setViewportView(jListCivics);

        jButtonUpdateCivics.setText("Update Civics");
        jButtonUpdateCivics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateCivicsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jButtonUpdateCivics)))
                .addContainerGap(392, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButtonUpdateCivics)
                .addGap(69, 69, 69))
        );

        jPanel3.add(jPanel5);

        jTabbedPaneEmpire.addTab("Civics", jPanel3);

        javax.swing.GroupLayout jPanelEmpireLayout = new javax.swing.GroupLayout(jPanelEmpire);
        jPanelEmpire.setLayout(jPanelEmpireLayout);
        jPanelEmpireLayout.setHorizontalGroup(
            jPanelEmpireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmpireLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEmpireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPaneEmpire)
                    .addGroup(jPanelEmpireLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(41, 41, 41)
                        .addComponent(jComboBoxEmpire, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelEmpireLayout.setVerticalGroup(
            jPanelEmpireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmpireLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelEmpireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxEmpire, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPaneEmpire)
                .addContainerGap())
        );

        jTabbedPanel1.addTab("Empire", jPanelEmpire);

        jLabel24.setText("Species");

        jComboBoxSpecies.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {" "}));
        jComboBoxSpecies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSpeciesActionPerformed(evt);
            }
        });

        jLabel25.setText("Trails");

        jListSpeciesTrails.setModel(this.speciesTraitsListModell);
        jListSpeciesTrails.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                }
                else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        jScrollPane3.setViewportView(jListSpeciesTrails);

        jButtonSpeciesUpdateChanges.setText("Update changes");
        jButtonSpeciesUpdateChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSpeciesUpdateChangesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxSpecies, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jButtonSpeciesUpdateChanges)))
                .addContainerGap(407, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBoxSpecies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonSpeciesUpdateChanges)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPanel1.addTab("Species", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanel1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanel1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFindSavegameFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindSavegameFileActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        this.jTextFieldSavegameFile.setText(chooser.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jButtonFindSavegameFileActionPerformed

    private void jTextFieldSavegameFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSavegameFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSavegameFileActionPerformed

    private void jCheckBoxUseTextFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxUseTextFileActionPerformed
        this.jTextFieldMetaFile.setEnabled(this.jCheckBoxUseTextFile.isSelected());
        this.jButtonFindMetaFile.setEnabled(this.jCheckBoxUseTextFile.isSelected());
        this.jTextFieldGamestateFile.setEnabled(this.jCheckBoxUseTextFile.isSelected());
        this.jButtonFindGamestateFile.setEnabled(this.jCheckBoxUseTextFile.isSelected());
    }//GEN-LAST:event_jCheckBoxUseTextFileActionPerformed

    private void jCheckBoxOtherSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOtherSaveFileActionPerformed
        this.jTextFieldNewFile.setEnabled(this.jCheckBoxOtherSaveFile.isSelected());
        this.jButtonFindNewFile.setEnabled(this.jCheckBoxOtherSaveFile.isSelected());
    }//GEN-LAST:event_jCheckBoxOtherSaveFileActionPerformed

    private void jCheckBoxUseTextFilePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCheckBoxUseTextFilePropertyChange

    }//GEN-LAST:event_jCheckBoxUseTextFilePropertyChange

    private void jButtonFindMetaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindMetaFileActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        this.jTextFieldMetaFile.setText(chooser.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jButtonFindMetaFileActionPerformed

    private void jButtonFindGamestateFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindGamestateFileActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        this.jTextFieldGamestateFile.setText(chooser.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jButtonFindGamestateFileActionPerformed

    private void jButtonFindNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindNewFileActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        this.jTextFieldNewFile.setText(chooser.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jButtonFindNewFileActionPerformed

    private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenActionPerformed
        try {
            if (this.jCheckBoxUseTextFile.isSelected()) {
                this.sgf.gamestatePath = this.jTextFieldMetaFile.getText();
                this.sgf.metaPath = this.jTextFieldGamestateFile.getText();
                this.sgf.loadTextFiles();
            } else {
                this.sgf.saveGameFilePath = this.jTextFieldSavegameFile.getText();
                this.sgf.loadZipFile();
            }
            this.sgf.stringToSGN();
            this.saveGame = new StellarisSaveGameObject(this.sgf.metaSGN, this.sgf.gameStateSGN);
            this.updateGameInfo();
            this.empireList = this.saveGame.getCountriesList();
            this.speciesList = this.saveGame.getSpeciesList();
            this.allCountryModifierList = this.saveGameParameter.getEmpireModifierDD();
            this.jComboBoxEmpire.setModel(new javax.swing.DefaultComboBoxModel<>(new Vector(this.empireList)));
            this.jComboBoxSpecies.setModel(new javax.swing.DefaultComboBoxModel<>(new Vector(this.speciesList)));
            this.jComboBoxEmpireActionPerformed(null);
            this.jComboBoxSpeciesActionPerformed(null);
            this.updateCountryModifierList();
            this.jTabbedPanel1.setEnabled(true);
        } catch (Exception e) {
            this.jTabbedPanel1.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonOpenActionPerformed

    private void jCheckBoxIronManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxIronManActionPerformed
        this.saveGame.setIronman(this.jCheckBoxIronMan.isSelected());
    }//GEN-LAST:event_jCheckBoxIronManActionPerformed

    private void jComboBoxEmpireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEmpireActionPerformed
        try {
            this.jTabbedPaneEmpire.setEnabled(true);
            this.updateEmpireRess();
            this.updateCountryCivics();
        } catch (Exception e) {
            this.jTabbedPanel1.setEnabled(false);
        }
    }//GEN-LAST:event_jComboBoxEmpireActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        this.sgf.metaString = this.saveGame.meta.toString();
        this.sgf.gamestateString = this.saveGame.gamestate.toString();
        String savePath = this.jTextFieldSavegameFile.getText();
        if (this.jCheckBoxOtherSaveFile.isSelected()) {
            savePath = this.jTextFieldNewFile.getText();
        }
        this.sgf.newSaveGameFilePath = savePath;
        this.sgf.saveZipFile();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jTextFieldNanitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNanitesActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "nanites", this.jTextFieldNanites.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldNanitesActionPerformed

    private void jTextFieldNanitesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldNanitesFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "nanites", this.jTextFieldNanites.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldNanitesFocusLost

    private void jTextFieldDarkMatterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDarkMatterActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_dark_matter", this.jTextFieldDarkMatter.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldDarkMatterActionPerformed

    private void jTextFieldDarkMatterFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldDarkMatterFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_dark_matter", this.jTextFieldDarkMatter.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldDarkMatterFocusLost

    private void jTextFieldZroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldZroActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_zro", this.jTextFieldZro.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldZroActionPerformed

    private void jTextFieldZroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldZroFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_zro", this.jTextFieldZro.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldZroFocusLost

    private void jTextFieldLivingMetalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLivingMetalActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_living_metal", this.jTextFieldLivingMetal.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldLivingMetalActionPerformed

    private void jTextFieldLivingMetalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldLivingMetalFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "sr_living_metal", this.jTextFieldLivingMetal.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldLivingMetalFocusLost

    private void jTextFieldRareCrystalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRareCrystalsActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "rare_crystals", this.jTextFieldRareCrystals.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldRareCrystalsActionPerformed

    private void jTextFieldRareCrystalsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldRareCrystalsFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "rare_crystals", this.jTextFieldRareCrystals.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldRareCrystalsFocusLost

    private void jTextFieldExoticGasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldExoticGasesActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "exotic_gases", this.jTextFieldExoticGases.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldExoticGasesActionPerformed

    private void jTextFieldExoticGasesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldExoticGasesFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "exotic_gases", this.jTextFieldExoticGases.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldExoticGasesFocusLost

    private void jTextFieldVolatileMotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldVolatileMotesActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "volatile_motes", this.jTextFieldVolatileMotes.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldVolatileMotesActionPerformed

    private void jTextFieldVolatileMotesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldVolatileMotesFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "volatile_motes", this.jTextFieldVolatileMotes.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldVolatileMotesFocusLost

    private void jTextFieldConsumerGoodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldConsumerGoodsActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "consumer_goods", this.jTextFieldConsumerGoods.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldConsumerGoodsActionPerformed

    private void jTextFieldConsumerGoodsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldConsumerGoodsFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "consumer_goods", this.jTextFieldConsumerGoods.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldConsumerGoodsFocusLost

    private void jTextFieldAlloysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAlloysActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "alloys", this.jTextFieldAlloys.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldAlloysActionPerformed

    private void jTextFieldAlloysFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldAlloysFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "alloys", this.jTextFieldAlloys.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldAlloysFocusLost

    private void jTextFieldUnityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUnityActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "unity", this.jTextFieldUnity.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldUnityActionPerformed

    private void jTextFieldUnityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldUnityFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "unity", this.jTextFieldUnity.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldUnityFocusLost

    private void jTextFieldInfluenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldInfluenceActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "influence", this.jTextFieldInfluence.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldInfluenceActionPerformed

    private void jTextFieldInfluenceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldInfluenceFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "influence", this.jTextFieldInfluence.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldInfluenceFocusLost

    private void jTextFieldInfluenceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldInfluenceFocusGained
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "influence", this.jTextFieldInfluence.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldInfluenceFocusGained

    private void jTextFieldFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFoodActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "food", this.jTextFieldFood.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldFoodActionPerformed

    private void jTextFieldFoodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldFoodFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "food", this.jTextFieldFood.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldFoodFocusLost

    private void jTextFieldMineralsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMineralsActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "minerals", this.jTextFieldMinerals.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldMineralsActionPerformed

    private void jTextFieldMineralsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldMineralsFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "minerals", this.jTextFieldMinerals.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldMineralsFocusLost

    private void jTextFieldEnergyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEnergyActionPerformed
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "energy", this.jTextFieldEnergy.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldEnergyActionPerformed

    private void jTextFieldEnergyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldEnergyFocusLost
        try {
            this.saveGame.setRess(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), "energy", this.jTextFieldEnergy.getText());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextFieldEnergyFocusLost

    private void jTextFieldNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNewFileActionPerformed
        try { 
        } catch (Exception e) {
        }        
    }//GEN-LAST:event_jTextFieldNewFileActionPerformed

    private void jComboBoxSpeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSpeciesActionPerformed
        try { 
            this.speciesTraitsListModell.clear();
            Set<String> s = new HashSet<>();    
            s.addAll(this.saveGameParameter.getSpeciesTraits());
            s.addAll(this.saveGame.getSpesiesTraitsList(((DropDownItem) this.jComboBoxSpecies.getSelectedItem()).getId()));
            List<String> tList = new ArrayList<>(s); 
            Collections.sort(tList);
            tList.forEach(t -> this.speciesTraitsListModell.addElement(t));
            
            this.saveGame.getSpesiesTraitsList(((DropDownItem) this.jComboBoxSpecies.getSelectedItem()).getId())
                    .forEach(t -> this.jListSpeciesTrails.setSelectedValue(t, true));
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jComboBoxSpeciesActionPerformed

    private void jButtonSpeciesUpdateChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSpeciesUpdateChangesActionPerformed
        try { 
            List<String> traitsList = this.jListSpeciesTrails.getSelectedValuesList();
            this.saveGame.updateSpeciesTraitsList(traitsList, ((DropDownItem) this.jComboBoxSpecies.getSelectedItem()).getId());
        } catch (Exception e) {
        }    
    }//GEN-LAST:event_jButtonSpeciesUpdateChangesActionPerformed

    private void jButtonUpdateCivicsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateCivicsActionPerformed
        try { 
            List<String> civicsList = this.jListCivics.getSelectedValuesList();
            this.saveGame.updateCountryCivicsList(((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId(), civicsList);
        } catch (Exception e) {
        }   
    }//GEN-LAST:event_jButtonUpdateCivicsActionPerformed

    private void jCheckBoxCountryActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxCountryActiveActionPerformed
        String cid = ((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId();
        String modifier = ((DropDownItem) this.jComboBoxSelectedCountryModifier.getSelectedItem()).getText();
        String days = this.jTextFieldCountryModifierDays.getText();
        if (!this.jCheckBoxCountryActive.isSelected()) {
            this.saveGame.delCountryModifier(cid, modifier);
        } else {
            this.saveGame.setCountryModifier(cid, modifier, days);
        } 
    }//GEN-LAST:event_jCheckBoxCountryActiveActionPerformed

    private void jTextFieldCountryModifierDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCountryModifierDaysActionPerformed
        this.jCheckBoxCountryActiveActionPerformed(evt);
    }//GEN-LAST:event_jTextFieldCountryModifierDaysActionPerformed

    private void jListCountryModifierListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListCountryModifierListValueChanged

    }//GEN-LAST:event_jListCountryModifierListValueChanged

    private void jComboBoxSelectedCountryModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSelectedCountryModifierActionPerformed
        String cid = ((DropDownItem) this.jComboBoxEmpire.getSelectedItem()).getId();
        String modifier = ((DropDownItem) this.jComboBoxSelectedCountryModifier.getSelectedItem()).getText();
        SaveGameNode node = this.saveGame.getContryModifier(cid, modifier);
        if (node==null) {
            this.jCheckBoxCountryActive.setSelected(false);
            this.jTextFieldCountryModifierDays.setText("");
        } else {
            System.out.println(node);
            this.jCheckBoxCountryActive.setSelected(true);
            this.jTextFieldCountryModifierDays.setText(node.getFirstChild().key("days"));
        }
        this.updateCountryModifierList();
    }//GEN-LAST:event_jComboBoxSelectedCountryModifierActionPerformed

    private void jTextFieldCountryModifierDaysFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldCountryModifierDaysFocusLost
        this.jComboBoxSelectedCountryModifierActionPerformed(null);
    }//GEN-LAST:event_jTextFieldCountryModifierDaysFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SSGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SSGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SSGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SSGE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SSGE().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFindGamestateFile;
    private javax.swing.JButton jButtonFindMetaFile;
    private javax.swing.JButton jButtonFindNewFile;
    private javax.swing.JButton jButtonFindSavegameFile;
    private javax.swing.JButton jButtonOpen;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSpeciesUpdateChanges;
    private javax.swing.JButton jButtonUpdateCivics;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBoxCountryActive;
    private javax.swing.JCheckBox jCheckBoxIronMan;
    private javax.swing.JCheckBox jCheckBoxOtherSaveFile;
    private javax.swing.JCheckBox jCheckBoxUseTextFile;
    private javax.swing.JComboBox<String> jComboBoxEmpire;
    private javax.swing.JComboBox<String> jComboBoxSelectedCountryModifier;
    private javax.swing.JComboBox<String> jComboBoxSpecies;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListCivics;
    private javax.swing.JList<String> jListCountryModifierList;
    private javax.swing.JList<String> jListSpeciesTrails;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelEmpire;
    private javax.swing.JPanel jPanelFile;
    private javax.swing.JPanel jPanelGame;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPaneModifier;
    private javax.swing.JTabbedPane jTabbedPaneEmpire;
    private javax.swing.JTabbedPane jTabbedPanel1;
    private javax.swing.JTextArea jTextAreaDLCS;
    private javax.swing.JTextField jTextFieldAlloys;
    private javax.swing.JTextField jTextFieldConsumerGoods;
    private javax.swing.JTextField jTextFieldCountryModifierDays;
    private javax.swing.JTextField jTextFieldDarkMatter;
    private javax.swing.JTextField jTextFieldDate;
    private javax.swing.JTextField jTextFieldEnergy;
    private javax.swing.JTextField jTextFieldExoticGases;
    private javax.swing.JTextField jTextFieldFood;
    private javax.swing.JTextField jTextFieldGamestateFile;
    private javax.swing.JTextField jTextFieldInfluence;
    private javax.swing.JTextField jTextFieldLivingMetal;
    private javax.swing.JTextField jTextFieldMetaFile;
    private javax.swing.JTextField jTextFieldMinerals;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldNanites;
    private javax.swing.JTextField jTextFieldNewFile;
    private javax.swing.JTextField jTextFieldRareCrystals;
    private javax.swing.JTextField jTextFieldSavegameFile;
    private javax.swing.JTextField jTextFieldUnity;
    private javax.swing.JTextField jTextFieldVersion;
    private javax.swing.JTextField jTextFieldVolatileMotes;
    private javax.swing.JTextField jTextFieldZro;
    // End of variables declaration//GEN-END:variables
}
