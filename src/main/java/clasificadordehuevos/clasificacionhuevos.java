//++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//                                                      //
// Class:  clasificacionhuevos                           //
//                                                      //
// Author: Automatically generated by Xfuzzy            //
//                                                      //
// Description: Fuzzy inference engine "clasificacionhuevos"       //
//                                                      //
//++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package clasificadordehuevos;

public class clasificacionhuevos implements FuzzyInferenceEngine {

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //  Rulebase RL_Base  //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 private MembershipFunction[] RL_Base(MembershipFunction Peso, MembershipFunction Color) {
  double _rl;
  double _input[] = new double[2];
  if(Peso instanceof FuzzySingleton)
   _input[0] = ((FuzzySingleton) Peso).getValue();
  if(Color instanceof FuzzySingleton)
   _input[1] = ((FuzzySingleton) Color).getValue();
  OP_clasificacionhuevos__default_ _op = new OP_clasificacionhuevos__default_();
  OutputMembershipFunction Tipo = new OutputMembershipFunction();
  Tipo.set(9,_op,_input);
  TP_clasificacionhuevos_Tpeso _t_Peso = new TP_clasificacionhuevos_Tpeso();
  TP_clasificacionhuevos_Tcolor _t_Color = new TP_clasificacionhuevos_Tcolor();
  TP_clasificacionhuevos_Ttipo _t_Tipo = new TP_clasificacionhuevos_Ttipo();
  int _i_Tipo=0;
  _rl = _op.and(_t_Peso.S.isEqual(Peso),_t_Color.Oscuro.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.CUARTA); _i_Tipo++;
  _rl = _op.and(_t_Peso.S.isEqual(Peso),_t_Color.Rosado.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.PRIMERA); _i_Tipo++;
  _rl = _op.and(_t_Peso.S.isEqual(Peso),_t_Color.Blanco.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.SEGUNDA); _i_Tipo++;
  _rl = _op.and(_t_Peso.M.isEqual(Peso),_t_Color.Oscuro.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.CUARTA); _i_Tipo++;
  _rl = _op.and(_t_Peso.M.isEqual(Peso),_t_Color.Rosado.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.PRIMERA); _i_Tipo++;
  _rl = _op.and(_t_Peso.M.isEqual(Peso),_t_Color.Blanco.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.SEGUNDA); _i_Tipo++;
  _rl = _op.and(_t_Peso.L.isEqual(Peso),_t_Color.Oscuro.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.CUARTA); _i_Tipo++;
  _rl = _op.and(_t_Peso.L.isEqual(Peso),_t_Color.Rosado.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.TERCERA); _i_Tipo++;
  _rl = _op.and(_t_Peso.L.isEqual(Peso),_t_Color.Blanco.isEqual(Color));
  Tipo.set(_i_Tipo,_rl, _t_Tipo.TERCERA); _i_Tipo++;
  MembershipFunction[] _output = new MembershipFunction[1];
  _output[0] = Tipo;
  return _output;
 }

 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//
 //               Fuzzy Inference Engine                //
 //+++++++++++++++++++++++++++++++++++++++++++++++++++++//

 public double[] crispInference(double[] _input) {
  MembershipFunction Peso = new FuzzySingleton(_input[0]);
  MembershipFunction Color = new FuzzySingleton(_input[1]);
  MembershipFunction Tipo;
  MembershipFunction[] _call;
  _call = RL_Base(Peso,Color); Tipo=_call[0];
  double _output[] = new double[1];
  if(Tipo instanceof FuzzySingleton)
   _output[0] = ((FuzzySingleton) Tipo).getValue();
  else _output[0] = ((OutputMembershipFunction) Tipo).defuzzify();
  return _output;
 }

 public double[] crispInference(MembershipFunction[] _input) {
  MembershipFunction Peso = _input[0];
  MembershipFunction Color = _input[1];
  MembershipFunction Tipo;
  MembershipFunction[] _call;
  _call = RL_Base(Peso,Color); Tipo=_call[0];
  double _output[] = new double[1];
  if(Tipo instanceof FuzzySingleton)
   _output[0] = ((FuzzySingleton) Tipo).getValue();
  else _output[0] = ((OutputMembershipFunction) Tipo).defuzzify();
  return _output;
 }

 public MembershipFunction[] fuzzyInference(double[] _input) {
  MembershipFunction Peso = new FuzzySingleton(_input[0]);
  MembershipFunction Color = new FuzzySingleton(_input[1]);
  MembershipFunction Tipo;
  MembershipFunction[] _call;
  _call = RL_Base(Peso,Color); Tipo=_call[0];
  MembershipFunction _output[] = new MembershipFunction[1];
  _output[0] = Tipo;
  return _output;
 }

 public MembershipFunction[] fuzzyInference(MembershipFunction[] _input) {
  MembershipFunction Peso = _input[0];
  MembershipFunction Color = _input[1];
  MembershipFunction Tipo;
  MembershipFunction[] _call;
  _call = RL_Base(Peso,Color); Tipo=_call[0];
  MembershipFunction _output[] = new MembershipFunction[1];
  _output[0] = Tipo;
  return _output;
 }

}
