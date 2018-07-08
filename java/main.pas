begin
  var fields: dictionary<string, string>;
  fields := system.IO.File.ReadLines('fields.csv').Select(x -> x.split(',')).Where(y -> ((y[2] = '2') or (y[2] = '0'))).todictionary(x -> x[0], y -> y[1]);
  var methods: dictionary<string, string>;
  methods := system.IO.File.ReadLines('methods.csv').Select(x -> x.split(',')).Where(y -> ((y[2] = '2') or (y[2] = '0'))).todictionary(x -> x[0], y -> y[1]);
  var parameters: dictionary<string, string>;
  parameters := system.IO.File.ReadLines('params.csv').Select(x -> x.split(',')).Where(y -> ((y[2] = '2') or (y[2] = '0'))).todictionary(x -> x[0], y -> y[1]);
  foreach var f: string in system.IO.Directory.GetFiles('ehacks', '*.java', system.IO.SearchOption.AllDirectories) do
  begin
    var ts: string := system.IO.File.ReadAllText(f);
    writeln('processing: ' + f);
    foreach var o in fields do
      ts := ts.Replace(o.Key, o.Value);
    foreach var o in methods do
      ts := ts.Replace(o.Key, o.Value);
    foreach var o in parameters do
      ts := ts.Replace(o.Key, o.Value);
    ts := ts.Replace('cheatingessentials', 'ehacks');
    writeln('ready: ' + f);
    system.IO.File.WriteAllText(f, ts);
  end;
end.