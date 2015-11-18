# -*- coding:utf8 -*-
"""
    原始模板，进一步聚类处理
"""
from StringIO import StringIO
import os
import re
import sys
import time

sys.path.append('%s/credit-python' % os.path.abspath(os.curdir))

from credit import o_templates, credit_type
from utils import diff_main_services

reload(sys)
sys.setdefaultencoding('utf8')
__author__ = 'hanjiewu'

credit_types = {}


def t_cmp(item1, item2):
    t_type = item1[1][0].t_type

    count1 = 0
    count2 = 0

    for tt in item1[1]:
        count1 += tt.c_count
    for tt in item2[1]:
        count2 += tt.c_count

    if t_type in credit_types:
        rule = credit_types[t_type]
        match1 = rule.is_match(item1[0])
        match2 = rule.is_match(item2[0])

        if match1 and match1 == match2:
            return i_cmp(count1, count2, len(item1[0]), len(item2[0]))
        elif match1:
            return -1
        elif match2:
            return 1
        else:
            match1 = credit_type.is_match_or_list(item1[0], rule.pos_rule_list)
            match2 = credit_type.is_match_or_list(item2[0], rule.pos_rule_list)

            if match1 == match2:
                return i_cmp(count1, count2, len(item1[0]), len(item2[0]))
            elif match1:
                return -1
            elif match2:
                return 1
    return i_cmp(count1, count2, len(item1[0]), len(item2[0]))


def i_cmp(count1, count2, len1, len2):
    if count1 > count2:
        return -1
    elif count2 > count1:
        return 1
    else:
        if len1 > len2:
            return -1
        elif len2 > len2:
            return 1
        else:
            return 0


def id_cmp(item1, item2):
    count1 = 0
    count2 = 0

    for item in item1[1]:
        count1 += tpl_count_map[get_key(item)]
    for item in item2[1]:
        count2 += tpl_count_map[get_key(item)]

    return i_cmp(count1, count2, len(item1[1]), len(item2[1]))


clusters = {}
dictionary = {}
id_dictionary = {}
tpl_index_map = {}
tpl_count_map = {}


def clear():
    clusters = {}
    dictionary = {}
    id_dictionary = {}
    tpl_index_map = {}
    tpl_count_map = {}
    credit_types = {}


def getId(seg):
    if dictionary.has_key(seg):
        return dictionary[seg]
    else:
        id = len(dictionary) + 1
        dictionary[seg] = id
        id_dictionary[id] = seg
        return id


split_pattern = re.compile(' \{[0-9]+} ?')


def build_id_list(tpl):
    seg_list = split_pattern.split(tpl)
    id_list = []
    for seg in seg_list:
        id_list.append(getId(seg))

    return id_list


def get_similary_id_list(id_list):
    l_len = len(id_list)
    ret = []

    if tpl_index_map.has_key(l_len):
        lists = tpl_index_map[l_len]
        for in_list in lists:
            diffs = 0
            for i in range(0, l_len):
                if id_list[i] != in_list[i]:
                    diffs += 1
            if (diffs < 3 and l_len >= 5) or (diffs == 1 and 5 > l_len >= 3):
                ret.append(in_list)
                if get_key(in_list) not in clusters:
                    print in_list
            if len(ret) > 100:
                break
    if len(ret) > 100:
        # print "Similary: %d" % len(ret)
        return []
    return ret


def add_tpl(id_list):
    build_tpl_index(id_list)
    lists = []
    lists.append(id_list)
    clusters[get_key(id_list)] = lists


def add_merge_tpl(id_list, new_id_list, old_id_list):
    if new_id_list == old_id_list:
        clusters[get_key(new_id_list)].append(id_list)
    else:
        kk = get_key(old_id_list)
        lists = clusters[kk]
        lists.append(id_list)
        del clusters[kk]
        clusters[get_key(new_id_list)] = lists
        remove_tpl_index(old_id_list)
        build_tpl_index(new_id_list)


def get_key(id_list):
    builder = StringIO()
    l_len = len(id_list)
    for i in range(l_len):
        if i != 0:
            builder.write(',')
        builder.write(id_list[i])
    return builder.getvalue()


def remove_tpl_index(id_list):
    l_len = len(id_list)
    in_list = tpl_index_map[l_len]
    while id_list in in_list:
        in_list.remove(id_list)


def build_tpl_index(id_list):
    l_len = len(id_list)

    if tpl_index_map.has_key(l_len):
        tpl_index_map[l_len].append(id_list)
    else:
        lists = []
        tpl_index_map[l_len] = lists
        lists.append(id_list)
    return id_list


def get_original_tpl(id_list):
    builder = StringIO()
    l_len = len(id_list)
    for i in range(l_len):
        index = i + 1
        builder.write('%s' % id_dictionary[int(id_list[i])])
        if index != l_len:
            builder.write(' {%d} ' % index)

    return builder.getvalue()


def cluster(templates, credit_type_ex_path):
    rule_list, or_rules = credit_type.load_rule_list(credit_type_ex_path)

    for rule in rule_list:
        credit_types[rule.type] = rule

    lines = 0
    starts = time.time()
    t_type = None
    for template in templates:
        lines += 1

        if lines % 100 == 0:
            print 'Lines: %d, templates: %d, use times: %s' % (lines, len(clusters), time.time() - starts)
        t_type = template.t_type
        if template.t_type in credit_types:
            rule = credit_types[template.t_type]
            if not rule.is_match(template.template):
                continue
        # 将模板转化为id表示的数组
        id_list = build_id_list(template.template)
        tpl_count_map[get_key(id_list)] = int(template.c_count)
        similary_id_list = get_similary_id_list(id_list)

        if len(similary_id_list) == 0:
            add_tpl(id_list)
        else:
            is_merge = False
            for similary in similary_id_list:
                can_merge = True
                new_id_list = []
                for i in range(len(id_list)):
                    if id_list[i] != similary[i]:
                        tpl_seg = diff_main_services.some_main_module(id_dictionary[id_list[i]],
                                                                      id_dictionary[similary[i]])
                        if not tpl_seg:
                            can_merge = False
                            break
                        new_id_list.append(getId(tpl_seg))
                    else:
                        new_id_list.append(id_list[i])
                if can_merge:
                    add_merge_tpl(id_list, new_id_list, similary)
                    is_merge = True
                    break
            if not is_merge:
                add_tpl(id_list)

    items = sorted(clusters.items(), cmp=id_cmp)
    return items


def process(template_path, output_template_path, credit_type_ex_path):
    templates = o_templates.load_templates(template_path)
    items = cluster(templates, credit_type_ex_path)
    o_file = open(output_template_path, mode='w')
    t_type = templates[0].t_type
    print 'finish cluster'
    # 仅提取关注的模板
    for id_list, lists in items:
        if len(lists) > 1:
            c_count = 0
            for ll in lists:
                c_count += tpl_count_map[get_key(ll)]
            str_lists = id_list.split(',')
            int_lists = []
            for id in str_lists:
                int_lists.append(int(id))

            o_file.write('%s_t\t%d\t%s\n' % (t_type, c_count, get_original_tpl(int_lists)))

            # limit = 0
            # for ll in lists:
            # limit += 1
            # if limit > 5:
            # break
            # o_file.write('%s\t%d\t%s\n' % (t_type, tpl_count_map[get_key(ll)], get_original_tpl(ll)))
            o_file.write('\n\n')
        else:
            for ll in lists:
                # if tpl_count_map[get_key(ll)] < 10:
                # continue
                o_file.write('%s\t%d\t%s\n' % (t_type, tpl_count_map[get_key(ll)], get_original_tpl(ll)))

    o_file.close()


if __name__ == '__main__':
    if len(sys.argv) != 4:
        sys.stderr.write('Usage: %s <template path> <output template path> <credit type ex path>' % sys.argv[0])
        sys.exit(-1)
    template_path = sys.argv[1]
    output_template_path = sys.argv[2]
    credit_type_ex_path = sys.argv[3]

    # 测试
    # template_path = r"F:\workspace-intellij-idea\credit-python\data\fa1_sample.txt"
    # output_template_path = r"F:\workspace-intellij-idea\credit-python\data\fa1_cluster"
    # credit_type_ex_path = r"F:\workspace-intellij-idea\credit-python\configs\credit-type-ex.json"
    process(template_path, output_template_path, credit_type_ex_path)
